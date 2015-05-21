/**
 * Copyright (C) 2011 Red Hat, Inc. (jdcasey@commonjava.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.aprox.change.event;

import java.util.Collection;

import org.commonjava.aprox.model.core.ArtifactStore;

/**
 * Event signaling that one or more specified {@link ArtifactStore} instances' configurations were changed. The {@link ArtifactStoreUpdateType}
 * gives more information about the nature of the update.
 * <br/>
 * This event is fired <b>AFTER</b> the updated {@link ArtifactStore} is actually persisted.
 */
public class ArtifactStorePostUpdateEvent
    extends ArtifactStoreUpdateEvent
{

    public ArtifactStorePostUpdateEvent( final ArtifactStoreUpdateType type, final Collection<ArtifactStore> changes )
    {
        super( type, changes );
    }

    public ArtifactStorePostUpdateEvent( final ArtifactStoreUpdateType type, final ArtifactStore... changes )
    {
        super( type, changes );
    }
}
