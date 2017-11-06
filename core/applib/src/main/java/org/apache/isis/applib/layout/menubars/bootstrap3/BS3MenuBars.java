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
package org.apache.isis.applib.layout.menubars.bootstrap3;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.layout.component.ServiceActionLayoutData;
import org.apache.isis.applib.layout.menubars.Menu;
import org.apache.isis.applib.layout.menubars.MenuSection;

/**
 * Describes the collection of domain services into menubars, broadly corresponding to the aggregation of information within {@link org.apache.isis.applib.annotation.DomainServiceLayout}.
 */
@XmlRootElement(
        name = "menuBars"
)
@XmlType(
        name = "menuBars"
        , propOrder = {
            "primary",
            "secondary",
            "tertiary",
            "metadataError"
        }
)
public class BS3MenuBars extends org.apache.isis.applib.layout.menubars.MenuBarsAbstract {

    private static final long serialVersionUID = 1L;

    public BS3MenuBars() {
    }

    private BS3MenuBar primary = new BS3MenuBar();

    public BS3MenuBar getPrimary() {
        return primary;
    }

    public void setPrimary(final BS3MenuBar primary) {
        this.primary = primary;
    }

    private BS3MenuBar secondary = new BS3MenuBar();

    public BS3MenuBar getSecondary() {
        return secondary;
    }

    public void setSecondary(final BS3MenuBar secondary) {
        this.secondary = secondary;
    }

    private BS3MenuBar tertiary = new BS3MenuBar();

    public BS3MenuBar getTertiary() {
        return tertiary;
    }

    public void setTertiary(final BS3MenuBar tertiary) {
        this.tertiary = tertiary;
    }

    public BS3MenuBar menuBarFor(final DomainServiceLayout.MenuBar menuBar) {
        switch (menuBar) {
        case PRIMARY:
            return getPrimary();
        case SECONDARY:
            return getSecondary();
        case TERTIARY:
            return getTertiary();
        }
        return null;
    }

    @Override
    public void visit(final Visitor visitor) {
        traverseMenuBar(getPrimary(), visitor);
        traverseMenuBar(getSecondary(), visitor);
        traverseMenuBar(getTertiary(), visitor);
    }

    private void traverseMenuBar(final BS3MenuBar menuBar, final Visitor visitor) {
        visitor.preVisit(menuBar);
        visitor.visit(menuBar);
        for (Menu menu : menuBar.getMenus()) {
            traverseMenu(menu, visitor);
        }
        visitor.postVisit(menuBar);
    }

    private void traverseMenu(final Menu menu, final Visitor visitor) {
        visitor.preVisit(menu);
        visitor.visit(menu);
        final List<MenuSection> sections = menu.getSections();
        for (MenuSection section : sections) {
            traverseSection(section, visitor);
        }
        visitor.postVisit(menu);
    }

    private void traverseSection(final MenuSection section, final Visitor visitor) {
        visitor.preVisit(section);
        visitor.visit(section);
        final List<ServiceActionLayoutData> actions = section.getActions();
        for (ServiceActionLayoutData action : actions) {
            visitor.visit(action);
        }
        visitor.postVisit(section);
    }

    private String metadataError;

    /**
     * For diagnostics; populated by the framework if and only if a metadata error.
     */
    @XmlElement(required = false)
    public String getMetadataError() {
        return metadataError;
    }

    public void setMetadataError(final String metadataError) {
        this.metadataError = metadataError;
    }

}