/**
 * <copyright> 
 *
 * Copyright (c) 2002-2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: RoseUtil.java,v 1.4.2.1 2005/06/08 18:27:44 nickb Exp $
 */
package org.eclipse.emf.codegen.ecore.rose2ecore;


import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.emf.codegen.ecore.CodeGenEcorePlugin;
import org.eclipse.emf.codegen.ecore.rose2ecore.parser.RoseLexer;
import org.eclipse.emf.codegen.ecore.rose2ecore.parser.RoseLoader;
import org.eclipse.emf.codegen.ecore.rose2ecore.parser.RoseNode;
import org.eclipse.emf.codegen.ecore.rose2ecore.parser.RoseParser;
import org.eclipse.emf.codegen.ecore.rose2ecore.parser.Util;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;


/**
 * Provide functions to process a rose file.
 */
public class RoseUtil 
{
  Map quidTable = new HashMap();
  Map nameTable = new HashMap();
  Map superTable = new HashMap();
  Map refTable = new HashMap();
  Map typeTable = new HashMap();
  Map primitiveTable = new HashMap();
  Map variableToDirectoryMap = new HashMap();
  Map packageNameToNSNameMap = new HashMap();
  Map packageNameToNSURIMap = new HashMap();
  Map ePackageToInformationMap = new HashMap();

  MultiStatus status = 
    new MultiStatus
      (CodeGenEcorePlugin.getPlugin().getBundle().getSymbolicName(), 
       0, 
       CodeGenEcorePlugin.INSTANCE.getString("_UI_ProblemsWereEncounteredLoadingTheRoseModel_message"),
       null);

  EcoreBuilder ecoreBuilder = new EcoreBuilder(this);

  public UnitTreeNode createRoseUnitTreeAndTable(String fileNameNodeValue, UnitTreeNode topNode) throws Exception
  {
    String fileName = resolveFileName(fileNameNodeValue);

    // Store the base name for relative .cat file references.
    //
    if (topNode == null)
    {
      int index = fileName.lastIndexOf(File.separator);
      if (index != -1)
      {
        variableToDirectoryMap.put(null, fileName.substring(0, index + 1));
      }
    }

    // read mdl file...
    RoseLoader loader = new RoseLoader(fileName);
    try
    {
      if (loader.isValid())
      {
        status = 
          new MultiStatus
            (CodeGenEcorePlugin.getPlugin().getBundle().getSymbolicName(), 
             1, 
             CodeGenEcorePlugin.INSTANCE.getString("_UI_ProblemsWereEncounteredConvertingTheRoseModel_message"),
             null);

        RoseLexer  lexer = new RoseLexer(loader);
        RoseParser parser = new RoseParser(lexer, true, true);
        parser.parse();
        RoseNode modelTree = parser.getModelTree();
        UnitTreeBuilder unitTreeBuilder = new UnitTreeBuilder(this);
        if (topNode == null) 
        {
          String qualifier;
          // special case, traverse cat file or mdl file
          //
          UnitTreeNode unitTree = null;
          if (modelTree.getKey().equals("") && (Util.getType(modelTree.getValue())).equals(RoseStrings.CLASS_CATEGORY)) 
          {
            // this is the case that starting process rose file by passing cat file name
            // this is a special case.
            // normally, user should pass mdl file instead of cat file
            // 
            // file is a cat file
            // modelTree did contain quid info
            String quid = modelTree.getRoseId();
            quid = quid.substring(1, quid.length()-1);
            unitTree = new UnitTreeNode(Util.getName(modelTree.getValue()), quid, fileName);
            String objName = Util.getName(modelTree.getValue());
            TableObject obj = new TableObject(objName, quid, unitTree);
            quidTable.put(quid, obj);
            nameTable.put(objName, obj);
            qualifier = objName;
          }
          else 
          {
            // file is a mdl file
            //
            // get model name
            int ind_1 = fileName.lastIndexOf(System.getProperty("file.separator"));
            int ind_2 = fileName.lastIndexOf(".");
            String modelName;
            if (ind_2 != -1)
              modelName = fileName.substring(ind_1+1, ind_2);
            else
              modelName = fileName.substring(ind_1+1, fileName.length());
            //if (!modelName.toLowerCase().endsWith("model"))
            //    modelName = modelName + "model";
            String quid = modelTree.getRoseId();
            if (quid != null) 
            {
              quid = quid.substring(1, quid.length()-1);
              unitTree = new UnitTreeNode(modelName, quid, fileName);
            } 
            else 
            {
              unitTree = new UnitTreeNode(modelName, "", fileName);
            }
            qualifier = null;
          }
          // starting traverse file and build unit tree and table info
          unitTreeBuilder.traverse(qualifier, modelTree, unitTree);
          return unitTree;
        } 
        else 
        {
          // cat(unit) file referenced by mdl file
          String quid = modelTree.getRoseId();
          if (quid != null) 
          {
            quid = quid.substring(1, quid.length()-1);
            topNode.setQUID(quid);
          }
          String objName = Util.getName(modelTree.getValue());
          TableObject obj = new TableObject(objName, quid, topNode);
          quidTable.put(quid, obj);
          nameTable.put(objName, obj);
          unitTreeBuilder.traverse(objName, modelTree, topNode);
          return null;
        }
      }
      else
      {
        getStatus().add
          (new Status
            (IStatus.ERROR,
             CodeGenEcorePlugin.getPlugin().getBundle().getSymbolicName(),
             0, 
             CodeGenEcorePlugin.INSTANCE.getString
               ("_UI_TheUnitResolvesTo_message", new Object [] { Util.trimQuotes(fileNameNodeValue), fileName }), 
             null));
        return null;
      }
    }
    finally
    {
      loader.close();
    }
  }

  public void showRoseUnitTree(UnitTreeNode unitTree)
  {
    if (unitTree != null) 
    {
      System.out.println(" ");
      System.out.println("======= Unit Tree Info =============");
      System.out.println("[0]: " + unitTree.getName() + ",   " + unitTree.getQUID() + ", " 
                                +  unitTree.getRoseFileName() + ",	" + unitTree.getEcoreFileName());
      int i = 1;
      traverseOut(unitTree, i);
    }

    if (quidTable.size() > 0) 
    {
      System.out.println("=========== Class Info ============");
      Iterator it = quidTable.keySet().iterator();
      while (it.hasNext()) 
      {
        Object key = it.next();
        TableObject obj = (TableObject)quidTable.get(key);
        System.out.println(key + ",\t" + obj.getName() + ",\t" + obj.getContainer().getEcoreFileName());
      }
    }
  }

  protected void traverseOut(UnitTreeNode tree, int index) 
  {
    List nodes = tree.getNodes();
    if (nodes.size() > 0) 
    {
      for (int i=0; i<nodes.size(); i++) 
      {
        UnitTreeNode node = (UnitTreeNode)nodes.get(i);
        System.out.println("[" + index + "]: " + node.getName() + ",  " + node.getQUID() + ", " + node.getEcoreFileName());
        traverseOut(node, index+1);
      }
    }
  }

  public void createExtent4RoseUnitTree(UnitTreeNode unitTree)
  {
    if (unitTree != null) 
    {
      checkConflictFileName(unitTree);
      createExtent(unitTree);
    }
    refTable.clear();
  }

  public void checkConflictFileName(UnitTreeNode unitTree)
  {
    String rootEcoreFileName = unitTree.getEcoreFileName();
    if (checkFileName(unitTree, rootEcoreFileName)) 
    {
      int index = rootEcoreFileName.lastIndexOf(".");
      if (index != -1)
      {
        rootEcoreFileName = 
          rootEcoreFileName.substring(0, index) + 
            "model" + 
            rootEcoreFileName.substring(index, rootEcoreFileName.length());
      }
      unitTree.setEcoreFileName(rootEcoreFileName);
    }
  }

  public boolean checkFileName(UnitTreeNode unitTree, String name)
  {
    List nodes = unitTree.getNodes();
    for (int i=0; i<nodes.size(); i++) 
    {
      UnitTreeNode node = (UnitTreeNode)nodes.get(i);
      if (node.getEcoreFileName().equals(name) || checkFileName(node, name))
      {
        return true;
      }
    }

    return false;
  }

  public void createExtent(UnitTreeNode unitTree)
  {
    EList ext = new BasicEList();
    unitTree.setExtent(ext);
    List nodes = unitTree.getNodes();
    for (int i=0; i<nodes.size(); i++) 
    {
      createExtent((UnitTreeNode)nodes.get(i));
    }
  }

  public void processUnitTree(UnitTreeNode unitTree) throws Exception
  {
    if (unitTree != null) 
    {
      loadTree(null, unitTree);
      String packageName = unitTree.getEcoreFileName();
      int fileSeparatorIndex = packageName.lastIndexOf(File.separator);
      if (fileSeparatorIndex != -1)
      {
        packageName = packageName.substring(fileSeparatorIndex + 1);
      }
      int dotIndex = packageName.lastIndexOf(".");
      if (dotIndex != -1)
      {
        packageName = packageName.substring(0, dotIndex);
      }
      ecoreBuilder.createEPackageForRootClasses(unitTree.getExtent(), unitTree.getRoseNode(), packageName);
      ecoreBuilder.setEEnums();
      ecoreBuilder.setEReferences();
      ecoreBuilder.setSuper();
      ecoreBuilder.setETypeClassifier();
      setIDs(unitTree);
      validate(unitTree);
    }
  }

  protected void setIDs(UnitTreeNode node) throws Exception
  {
    for (Iterator i = node.getExtent().iterator(); i.hasNext(); )
    {
      ecoreBuilder.setIDs(null, (EObject)i.next());
    }

    // Process the children of the UnitTreeNode recursively.
    //
    for (Iterator i = node.getNodes().iterator(); i.hasNext(); )
    {
      setIDs((UnitTreeNode)i.next());
    }
  }

  protected void validate(UnitTreeNode node) throws Exception
  {
    // Process the contents of the extent
    //
    for (Iterator i = node.getExtent().iterator(); i.hasNext(); )
    {
      ecoreBuilder.validate((EObject)i.next());
    }

    // Process the children of the UnitTreeNode recursively.
    //
    for (Iterator i = node.getNodes().iterator(); i.hasNext(); )
    {
      validate((UnitTreeNode)i.next());
    }
  }

  protected void loadTree(RoseNode containingNode, UnitTreeNode node) throws Exception
  {
    // Load the Rose .mdl or .cat file, and create mappings for the objects.
    //
    String name = node.getName();
    String roseFile = node.getRoseFileName();
    RoseLoader loader = new RoseLoader(roseFile);
    try
    {
      if (loader.isValid())
      {
        RoseLexer  lexer = new RoseLexer(loader);
        RoseParser parser = new RoseParser(lexer, true, true);
        parser.parse();
        RoseNode modelTree = parser.getModelTree();
        modelTree.setNode(node.getExtent());

        // This sets the parent so that the nodes can traverse to the root to find default eCore settings.
        //
        if (containingNode != null)
        {
          modelTree.setParent(containingNode);
        }

        containingNode = modelTree;

        // Start second traverse to create mapping objects in memory.
        //
        RoseWalker roseWalker = new RoseWalker(modelTree);
        roseWalker.traverse(ecoreBuilder);
      }
    }
    finally 
    {
      loader.close();
    }

    // Process the children of the UnitTreeNode recursively.
    //
    for (Iterator i = node.getNodes().iterator(); i.hasNext(); )
    {
      UnitTreeNode subNode = (UnitTreeNode)i.next();
      loadTree(containingNode, subNode);
    }
  }

  public void saveEcoreFiles(ResourceSet resourceSet) throws Exception
  {
    for (Iterator it = resourceSet.getResources().iterator(); it.hasNext(); ) 
    {
      Resource resource = (Resource)it.next();
      resource.save(Collections.EMPTY_MAP);
    }
  }

  public void createResource(UnitTreeNode tree, ResourceSet resourceSet)
  {
    EList ext = tree.getExtent();
    if (ext.size() > 0) 
    {
      String ecoreFileName = tree.getEcoreFileName();
      URI ecoreURI = URI.createURI(ecoreFileName);
      Resource res = Resource.Factory.Registry.INSTANCE.getFactory(ecoreURI).createResource(ecoreURI);
      res.getContents().addAll(tree.getExtent());
      resourceSet.getResources().add(res);
    }
    List nodes = tree.getNodes();
    for (int i=0; i<nodes.size(); i++) 
    {
      createResource((UnitTreeNode)nodes.get(i), resourceSet);
    }
  }

  public String resolveFileName(String name)
  {
    name = Util.trimQuotes(name);
    name = Util.updateFileName(name, "\\\\");
    name = Util.updateFileName(name, "\\");
    name = Util.updateFileName(name, "/");

    String result = "";
    int index;
    while ((index = name.indexOf(File.separator)) != -1) 
    {
      String directoryName = name.substring(0, index);
      if (directoryName.startsWith("$")) //directoryName.length() > 0 && directoryName.charAt(0) == '$') 
      {
        String variableName = directoryName.substring(1);
        directoryName = (String) variableToDirectoryMap.get(variableName);
        if (directoryName == null)
        {
          variableToDirectoryMap.put(variableName, null);
        }
      }
      result += directoryName + File.separator;
      name = name.substring(index + 1);
    }
    result += name;
    if (result.indexOf(":") == -1 && !result.startsWith(File.separator))
    {
      String baseName = (String)variableToDirectoryMap.get(null);
      if (baseName != null)
      {
        result = baseName + result;
      }
    }
    return result;
  }

  public Map getVariableToDirectoryMap()
  {
    return variableToDirectoryMap;
  }

  public Map getPackageNameToNSNameMap()
  {
    return packageNameToNSNameMap;
  }

  public Map getPackageNameToNSURIMap()
  {
    return packageNameToNSURIMap;
  }

  public Map getEPackageToInformationMap()
  {
    return ePackageToInformationMap;
  }

  public MultiStatus getStatus()
  {
    return status;
  }
}