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

package org.apache.isis.core.metamodel.facets.properties.propertylayout;

import java.util.List;
import java.util.Objects;

import com.google.common.base.Strings;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.members.cssclass.CssClassFacet;
import org.apache.isis.core.metamodel.facets.members.cssclass.CssClassFacetAbstract;

public class CssClassFacetForPropertyLayoutAnnotation extends CssClassFacetAbstract {

    public static CssClassFacet create(
            final List<PropertyLayout> propertyLayouts,
            final FacetHolder holder) {

        return propertyLayouts.stream()
                .map(PropertyLayout::cssClass)
                .map(Strings::emptyToNull)
                .filter(Objects::nonNull)
                .findFirst()
                .map(cssClass -> new CssClassFacetForPropertyLayoutAnnotation(cssClass, holder))
                .orElse(null);
    }

    private CssClassFacetForPropertyLayoutAnnotation(String value, FacetHolder holder) {
        super(value, holder);
    }

}
