/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.isis.applib.services.jaxb;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.jaxb.JaxbService;
import org.apache.isis.applib.services.registry.ServiceRegistry;
import org.apache.isis.schema.utils.jaxbadapters.PersistentEntityAdapter;

@DomainService(
        nature = NatureOfService.DOMAIN,
        menuOrder = "" + Integer.MAX_VALUE
)
public class JaxbServiceDefault extends JaxbService.Simple {

    protected void configure(final Unmarshaller unmarshaller) {
        final PersistentEntityAdapter adapter = new PersistentEntityAdapter();
        serviceRegistry.injectServicesInto(adapter);
        unmarshaller.setAdapter(PersistentEntityAdapter.class, adapter);
    }

    protected void configure(final Marshaller marshaller) {
        final PersistentEntityAdapter adapter = new PersistentEntityAdapter();
        serviceRegistry.injectServicesInto(adapter);
        marshaller.setAdapter(PersistentEntityAdapter.class, adapter);
    }

    @javax.inject.Inject
    ServiceRegistry serviceRegistry;
}

