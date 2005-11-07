package org.nakedobjects.object;

import org.nakedobjects.object.control.Consent;

import java.io.Serializable;


public interface Action extends NakedObjectMember {
    public final static Type DEBUG = new Type("DEBUG");
    public final static Type EXPLORATION = new Type("EXPLORATION");
    public final static Type USER = new Type("USER");
    public final static Target LOCAL = new Target("LOCAL");
    public final static Target REMOTE = new Target("REMOTE");
    public final static Target DEFAULT = new Target("DEFAULT");

    public static class Type implements Serializable {
        private final static long serialVersionUID = 1L;
        private String name;

        private Type(String name) {
            this.name = name;
        }

        public boolean equals(Object object) {
            if (object instanceof Action.Type) {
                Action.Type type = (Action.Type) object;
                return name.equals(type.name);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return name.hashCode();
        }

        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }
    }
    
    public class Target {
        String name;

        private Target(String name) {
            this.name = name;
        }

        public boolean equals(Object object) {
            if (object instanceof Action.Target) {
                Action.Target type = (Action.Target) object;
                return name.equals(type.name);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return name.hashCode();
        }

        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }
    }

    int getParameterCount();

    Action.Type getType();

    Action.Target getTarget();

    /**
     * Returns true if the represented action returns something, else returns
     * false.
     */
    boolean hasReturn();

    NakedObjectSpecification[] parameterTypes();

    Naked[] parameterStubs();

    NakedObjectSpecification getReturnType();

    Naked execute(final NakedObject target, final Naked[] parameters);
        
    Consent hasValidParameters(NakedObject object, Naked[] parameters);
    
    ActionParameterSet getParameters(NakedObject object);
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