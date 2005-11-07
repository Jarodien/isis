package org.nakedobjects.object;

import org.nakedobjects.object.control.Consent;


public interface OneToOneAssociation extends NakedObjectField {

    void clearAssociation(NakedObject inObject, NakedObject associate);

    void clearValue(NakedObject inObject);

    /**
     * set up a field within an object - this call should not be transfered to a remote object
     */
    void initAssociation(NakedObject inObject, NakedObject associate);

    void initValue(NakedObject inObject, Object value);

    void setAssociation(NakedObject inObject, NakedObject associate);

    void setValue(NakedObject inObject, Object value);

    Consent isAssociationValid(NakedObject inObject, NakedObject associate);

    Consent isValueValid(NakedObject inObject, NakedValue value);
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business objects directly to the user.
 * Copyright (C) 2000 - 2005 Naked Objects Group Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address of Naked Objects Group is
 * Kingsway House, 123 Goldworth Road, Woking GU21 1NR, UK).
 */