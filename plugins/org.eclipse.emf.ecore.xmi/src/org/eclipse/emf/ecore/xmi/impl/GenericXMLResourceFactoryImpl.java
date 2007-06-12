/**
 * <copyright>
 *
 * Copyright (c) 2005-2006 IBM Corporation and others.
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
 * $Id: GenericXMLResourceFactoryImpl.java,v 1.5 2006/12/05 20:23:28 emerks Exp $
 */
package org.eclipse.emf.ecore.xmi.impl;


import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.XMLResource;


public class GenericXMLResourceFactoryImpl extends ResourceFactoryImpl
{
  public GenericXMLResourceFactoryImpl()
  {
    super();
  }

  @Override
  public Resource createResource(URI uri)
  {
    XMLResource result = new XMLResourceImpl(uri);
    result.setEncoding("UTF-8");

    result.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
    result.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

    result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

    result.getDefaultSaveOptions().put(XMLResource.OPTION_LINE_WIDTH, new Integer(80));

    result.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);

    result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

    XMLOptions xmlOptions = new XMLOptionsImpl();

    xmlOptions.setProcessAnyXML(true);

    xmlOptions.setProcessSchemaLocations(true);
    
    result.getDefaultLoadOptions().put(XMLResource.OPTION_XML_OPTIONS, xmlOptions);

    return result;
  }
}