/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.commonjava.aprox.core.content.group;

import java.util.Collection;

import org.commonjava.aprox.model.core.Group;
import org.commonjava.maven.galley.model.Transfer;

public interface MetadataMerger
{

    byte[] merge( final Collection<Transfer> sources, final Group group, final String path );

}
