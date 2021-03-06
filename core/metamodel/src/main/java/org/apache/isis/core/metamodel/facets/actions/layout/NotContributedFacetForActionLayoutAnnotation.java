/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.core.metamodel.facets.actions.layout;


import java.util.List;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.actions.notcontributed.NotContributedFacet;
import org.apache.isis.core.metamodel.facets.actions.notcontributed.NotContributedFacetAbstract;


public class NotContributedFacetForActionLayoutAnnotation extends NotContributedFacetAbstract {

    public static NotContributedFacet create(
            final List<ActionLayout> actionLayouts,
            final FacetHolder holder) {

        return actionLayouts.stream()
                .map(ActionLayout::contributed)
                .filter(contributed -> contributed != Contributed.NOT_SPECIFIED)
                .findFirst()
                .map(contributed -> {
                    NotContributedAs as = NotContributedAs.notFrom(contributed);
                    return new NotContributedFacetForActionLayoutAnnotation(as, contributed, holder);
                })
                .orElse(null);
    }

    private NotContributedFacetForActionLayoutAnnotation(
            final NotContributedAs as,
            final Contributed contributed,
            final FacetHolder holder) {
        super(as, contributed, holder);
    }

}
