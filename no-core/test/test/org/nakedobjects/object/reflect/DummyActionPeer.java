package test.org.nakedobjects.object.reflect;

import org.nakedobjects.object.Action;
import org.nakedobjects.object.ActionParameterSet;
import org.nakedobjects.object.Naked;
import org.nakedobjects.object.NakedObject;
import org.nakedobjects.object.NakedObjectSpecification;
import org.nakedobjects.object.Session;
import org.nakedobjects.object.control.Consent;
import org.nakedobjects.object.reflect.ActionParameterSetImpl;
import org.nakedobjects.object.reflect.ActionPeer;
import org.nakedobjects.object.reflect.MemberIdentifier;

import java.util.Vector;

import test.org.nakedobjects.utility.ExpectedSet;


public final class DummyActionPeer implements ActionPeer {
    private ExpectedSet expectedActions = new ExpectedSet();
    private Naked returnObject;
    private NakedObjectSpecification returnType;
    private NakedObjectSpecification[] paramterTypes = new NakedObjectSpecification[0];
    private String name;
    private String action_label;

    public Naked execute(NakedObject object, Naked[] parameters) {
        expectedActions.addActual("execute " + getIdentifier() + " " + object);
        return returnObject;
    }

    public Object getExtension(Class cls) {
        return null;
    }

    public MemberIdentifier getIdentifier() {
        return new DummyIdentifier(name);
    }

    public int getParameterCount() {
        return 0;
    }

    public ActionParameterSet createParameterSet(NakedObject object, Naked[] parameters) {
        return new ActionParameterSetImpl(new Object[] { new String(), new Integer(123), new Vector() }, new String[] { "one", "two",
                "three" }, new boolean[3]);
    }

    public Action.Target getTarget() {
        return null;
    }

    public Action.Type getType() {
        return null;
    }

    public NakedObjectSpecification[] getParameterTypes() {
        return paramterTypes;
    }

    public NakedObjectSpecification getReturnType() {
        return returnType;
    }
    
    public void verify() {
        expectedActions.verify();
    }
    
    public void expect(String string) {
        expectedActions.addExpected(string);
    }

    public void setupReturnType(NakedObjectSpecification returnType) {
        this.returnType = returnType;
    }

    public void setupReturnObject(Naked returnObject) {
        this.returnObject = returnObject;
    }
    
    public void setUpParamterTypes(NakedObjectSpecification[] paramterTypes) {
        this.paramterTypes = paramterTypes;
    }

    
    public void setupName(String name) {
        this.name = name;
    }

    public Consent isUsable(NakedObject target) {
        return null;
    }

    public Consent hasValidParameters(NakedObject object, Naked[] parameters) {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public Consent isVisible(NakedObject target) {
        return null;
    }

    public boolean isAuthorised(Session session) {
        return false;
    }

    public void setupLabel(String action_label) {
        this.action_label = action_label;}

    public Class[] getExtensions() {
        return null;
    }

    public String getName() {
        return null;
    }

}

/*
 * Naked Objects - a framework that exposes behaviourally complete business
 * objects directly to the user. Copyright (C) 2000 - 2005 Naked Objects Group
 * Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address
 * of Naked Objects Group is Kingsway House, 123 Goldworth Road, Woking GU21
 * 1NR, UK).
 */