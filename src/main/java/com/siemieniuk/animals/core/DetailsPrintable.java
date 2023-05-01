package com.siemieniuk.animals.core;

/**
 * This interface is implemented by all objects which could be displayed in information window
 */
public interface DetailsPrintable {
    /**
     * Sets object-specific string to describe the object's state
     * @return Text to display
     */
    String getDetails();
}
