package org.nakedobjects.viewer.skylark.basic;

import org.nakedobjects.object.Naked;
import org.nakedobjects.object.NakedClass;
import org.nakedobjects.object.NakedObject;
import org.nakedobjects.object.NakedObjectSpecification;
import org.nakedobjects.object.control.Permission;
import org.nakedobjects.object.control.defaults.Allow;
import org.nakedobjects.object.control.defaults.Veto;
import org.nakedobjects.object.reflect.AssociateCommand;
import org.nakedobjects.object.reflect.OneToOneAssociationSpecification;
import org.nakedobjects.object.security.ClientSession;
import org.nakedobjects.viewer.skylark.Canvas;
import org.nakedobjects.viewer.skylark.Color;
import org.nakedobjects.viewer.skylark.Content;
import org.nakedobjects.viewer.skylark.ContentDrag;
import org.nakedobjects.viewer.skylark.Location;
import org.nakedobjects.viewer.skylark.MenuOptionSet;
import org.nakedobjects.viewer.skylark.ObjectContent;
import org.nakedobjects.viewer.skylark.OneToOneField;
import org.nakedobjects.viewer.skylark.Size;
import org.nakedobjects.viewer.skylark.Style;
import org.nakedobjects.viewer.skylark.Text;
import org.nakedobjects.viewer.skylark.View;
import org.nakedobjects.viewer.skylark.ViewAxis;
import org.nakedobjects.viewer.skylark.ViewSpecification;
import org.nakedobjects.viewer.skylark.core.AbstractView;
import org.nakedobjects.viewer.skylark.special.LookupBorder;

import org.apache.log4j.Logger;


public class EmptyField extends AbstractView {

    public static class Specification implements ViewSpecification {
        public boolean canDisplay(Naked object) {
            return object == null;
        }

        public View createView(Content content, ViewAxis axis) {
            EmptyField emptyField = new EmptyField(content, this, axis);
            if (((OneToOneField) content).isLookup()) {
                return new ObjectBorder(new LookupBorder(emptyField));
            } else {
                return new ObjectBorder(emptyField);
            }
        }

        public String getName() {
            return "empty field";
        }

        public boolean isOpen() {
            return false;
        }

        public boolean isReplaceable() {
            return true;
        }

        public boolean isSubView() {
            return true;
        }
    }
    private static final Logger LOG = Logger.getLogger(EmptyField.class);
    private static Text style = Style.NORMAL;

    public EmptyField(Content content, ViewSpecification specification, ViewAxis axis) {
        super(content, specification, axis);
        if (!(content instanceof OneToOneField)) {
            throw new IllegalArgumentException("Content for EmptyField must be NullValueField: " + content);
        }
        NakedObject object = ((OneToOneField) getContent()).getObject();
        if (object != null) {
            throw new IllegalArgumentException("Content for EmptyField must be null: " + object);
        }
    }

    private Permission canDrop(NakedObject dragSource, NakedObject parent) {
        if (dragSource instanceof NakedClass) {
            return new Allow();
        } else {
            NakedObjectSpecification targetType = forSpecification(); //((OneToOneField)
                                                                                                                               // getContent()).getField().getType();

            NakedObjectSpecification sourceType = dragSource.getSpecification();
            if (!sourceType.isOfType(targetType)) {
                return new Veto("Can only drop objects of type " + targetType.getSingularName());
            }

            if (parent.getOid() != null && dragSource.getOid() == null) {
                return new Veto("Can't drop a non-persistent into this persistent object");
            }

            Permission perm = getEmptyField().getAbout(ClientSession.getSession(), parent, dragSource).canUse();
            return perm;
        }
    }

    public void dragIn(ContentDrag drag) {
        NakedObject target = ((ObjectContent) getParent().getContent()).getObject();
        NakedObject source = ((ObjectContent) drag.getSourceContent()).getObject();

        Permission perm = canDrop(source, target);
        if (perm.getReason() != null) {
            getViewManager().setStatus(perm.getReason());
        }

        if (perm.isAllowed()) {
            getState().setCanDrop();
        } else {
            getState().setCantDrop();
        }

        markDamaged();
    }

    public void dragOut(ContentDrag drag) {
        getState().clearObjectIdentified();

        markDamaged();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        Color color;

        if (getState().canDrop()) {
            color = Style.VALID;
        } else if (getState().cantDrop()) {
            color = Style.INVALID;
        } else if (getState().isViewIdentified()) {
            color = Style.PRIMARY1;
        } else {
            color = Style.SECONDARY1;
        }

        int iconHeight = (style.getHeight() * 120) / 100;
        int iconWidth = (iconHeight * 80) / 100;
        int containerHeight = getSize().getHeight();
        int iconCentre = containerHeight / 2;

        int xt = iconWidth + (HPADDING * 2);
        int yt = getBaseline();

        int xi = getPadding().getLeft() + HPADDING;
        int yi = iconCentre - (iconHeight / 2);

        canvas.drawSolidOval(xi, yi, iconWidth, iconHeight, color);
        canvas.drawText(name(), xt, yt, color, style);

        if (AbstractView.DEBUG) {
            Size size = getSize();
            canvas.drawRectangle(0, 0, size.getWidth() - 1, size.getHeight() - 1, Color.DEBUG3);
            canvas.drawLine(0, size.getHeight() / 2, size.getWidth() - 1, size.getHeight() / 2, Color.DEBUG3);
        }
    }

    public void drop(ContentDrag drag) {
        NakedObject target = ((ObjectContent) getParent().getContent()).getObject();
        NakedObject source = ((ObjectContent) drag.getSourceContent()).getObject();

        if (canDrop(source, target).isAllowed()) {

            NakedObject associatedObject;
            OneToOneAssociationSpecification field = getEmptyField();
            LOG.debug("drop " + source + " on " + field + "/" + target);
            if (source instanceof NakedClass) {
                associatedObject = ((NakedClass) source).newInstance();
            } else {
                associatedObject = source;
            }

            getViewManager().getUndoStack().add(new AssociateCommand(target, associatedObject, field));
            // field.setAssociation(target, associatedObject);

            boolean isNotPersistent = target.getOid() == null;
            if (isNotPersistent) {
                getParent().invalidateContent();
            }
        }
    }

    private NakedObjectSpecification forSpecification() {
        return getEmptyField().getType();
    }

    /**
     * @see View#getBaseline()
     */
    public int getBaseline() {
        int containerHeight = getSize().getHeight();
        int iconCentre = containerHeight / 2;
        int yt = iconCentre + (style.getAscent() / 2);

        return yt;
    }

    private OneToOneAssociationSpecification getEmptyField() {
        return (OneToOneAssociationSpecification) ((OneToOneField) getContent()).getField();
    }

    public Size getRequiredSize() {
        Size size = new Size(0, 0);
        int iconHeight = (style.getHeight() * 120) / 100;
        size.extendWidth((iconHeight * 80) / 100);
        size.extendWidth(HPADDING * 2);
        size.extendWidth(style.stringWidth(name()));

        size.setHeight(iconHeight);
        size.extendHeight(VPADDING * 2);
        return size;
    }

    protected String iconName() {
        String clsName = getEmptyField().getType().getFullName();
        return clsName.substring(clsName.lastIndexOf('.') + 1);
    }

    public void menuOptions(MenuOptionSet options) {
        ClassOption.menuOptions(forSpecification(), options);

        options.setColor(Style.CONTENT_MENU);
    }

    private String name() {
        OneToOneAssociationSpecification field = getEmptyField();

        if (field == null) {
            return "";
        } else {
            return forSpecification().getSingularName();
        }
    }

    /**
     * Objects returned by menus are used to set this field before passing the
     * call on to the parent.
     */
    public void objectActionResult(Naked result, Location at) {
        NakedObject target = ((ObjectContent) getParent().getContent()).getObject();
        OneToOneAssociationSpecification field = getEmptyField();
        if (field.getType().isOfType(result.getSpecification())) {
            field.setAssociation(target, (NakedObject) result);
        }
        super.objectActionResult(result, at);
    }

    protected String title() {
        return name();
    }

    public String toString() {
        return "EmptyField" + getId();
    }
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business
 * objects directly to the user. Copyright (C) 2000 - 2003 Naked Objects Group
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
