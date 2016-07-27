package org.robotframework.formslibrary.util;

import java.awt.Component;
import java.util.Comparator;

/**
 * Comparator to sort components by their location on the screen. Sorting is
 * done from top to bottom and left to right.
 */
public class ComponentComparator implements Comparator<Component> {

    @Override
    public int compare(Component c1, Component c2) {

        if (c1.getY() < c2.getY()) {
            return -1;
        } else if (c1.getY() == c2.getY()) {
            if (c1.getX() < c2.getX()) {
                return -1;
            } else if (c1.getX() == c2.getX()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

}
