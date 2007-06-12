/**
 * <copyright>
 *
 * Copyright (c) 2002-2006 IBM Corporation and others.
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
 * $Id: StandaloneZipTest.java,v 1.4 2006/12/28 06:58:13 marcelop Exp $
 */
package org.eclipse.emf.test.build;

import java.io.File;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StandaloneZipTest extends TestCase
{
  protected final static String JARS_DIR = "emf/bin";
  protected final static String[] STANDALONE_JARS =
  {"emf.common","emf.ecore","emf.ecore.xmi"
    ,"emf.edit","emf.ecore.edit"
    ,"emf.ecore.change.edit","emf.ecore.change"
    ,"emf.commonj.sdo","emf.ecore.sdo","emf.ecore.sdo.edit"
    ,"xsd","xsd.edit"
    ,"emf.mapping.ecore2xml"};
  
  protected final static String[] OTHER_FILES =
  {"readme_standalone.html","images/jars.gif"};
  
  protected File standaloneZipFile;
  
  public StandaloneZipTest(String name)
  {
    super(name);
  }

  public static Test suite()
  {
    TestSuite ts = new TestSuite("BuildTests");
    ts.addTest(new StandaloneZipTest("testContent"));
    return ts;
  }
  
  @Override
  protected void setUp() throws Exception
  {
    File testBuildDir = new File(TestUtil.getPluginDirectory()).getAbsoluteFile();
    File eclipseDir = testBuildDir.getParentFile().getParentFile();
    standaloneZipFile = getStandaloneZip(eclipseDir);
    
    if (standaloneZipFile == null)
    {
      //In the build environment, the eclipseDir is in testingDir/target/
      File testingDir = eclipseDir.getParentFile().getParentFile();
      standaloneZipFile = getStandaloneZip(testingDir);
    }
    
    assertNotNull("The standalone zip should be available in the eclipse dir ('" + eclipseDir.getAbsolutePath() + "')", standaloneZipFile);
  }
  
  protected File getStandaloneZip(File dir)
  {
    File[] files = dir.listFiles();
    for (int i = 0; i < files.length; i++)
    {
      File file = files[i];
      if (file.isFile() && file.getName().matches("^emf-sdo-xsd-Standalone-.*\\.zip$"))
      {
        return file;
      }
    }
    return null;
  }

  public void testContent() throws Exception
  {    
    Set<String> jarNames = new HashSet<String>(Arrays.asList(STANDALONE_JARS));
    Set<String> otherFiles = new HashSet<String>(Arrays.asList(OTHER_FILES));
    
    for (Enumeration<? extends ZipEntry> e = new ZipFile(standaloneZipFile).entries(); e.hasMoreElements(); )
    {
      ZipEntry entry = e.nextElement();
      String entryName = entry.getName();     
      if (entryName.endsWith(".jar"))
      {
        for (Iterator<String> i = jarNames.iterator(); i.hasNext();)
        {
          String jarName = i.next();
          if(entryName.startsWith(JARS_DIR + "/" + jarName + "_"))
          {
            i.remove();
          }
        }
      }
      else
      {
        otherFiles.remove(entryName);
      }
    }
    
    if (!jarNames.isEmpty())
    {
      StringBuffer missingJars = new StringBuffer();
      for ( String jarName : jarNames)
      {
        missingJars.append(" ,").append(jarName);
      }
      missingJars.deleteCharAt(1).insert(0, "Missing jars:");
      fail(missingJars.toString());
    }    

    if (!otherFiles.isEmpty())
    {
      StringBuffer missingFiles = new StringBuffer();
      for ( String jarName : otherFiles)
      {
        missingFiles.append(" ,").append(jarName);
      }
      missingFiles.deleteCharAt(1).insert(0, "Missing files:");
      fail(missingFiles.toString());
    }
  }
}