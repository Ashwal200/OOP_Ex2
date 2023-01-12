package Ex2_2;

import java.util.Arrays;

public class PriorityData {
    int arrayOfPriority[] = new int[10];

    /**
     * Method invoked prior to executing the given Runnable in the given thread.<br>
     * This method is invoked by thread t that will execute task r,<br>
     * and may be used to re-initialize ThreadLocals, or to perform logging.<br>
     */
    public void beforeExecuteUpdate(int index) {
        if (validatePriority(index))
            arrayOfPriority[index-1]--;
    }
    /**
     *
     * @return the number of the max priority in the array, each index represent a number of priority.<br>
     * Index 0 for COMPUTATIONAL, if is 0 continue.<br>
     * Index 1 for IO, if is 0 continue.<br>
     * Index 2 for OTHER, if is 0 continue.<br>
     * Repeat until the last index.<br>
     * Else return 0;
     */
    public int getCurrentMax(){
        for (int i = 0; i < 10; i++) {
            if (arrayOfPriority[i] > 0)
                return i+1;
        }
        return 0;
    }

    /**
     * priority is represented by an integer value, ranging from 1 to 10
     *
     * @param priority
     * @return whether the priority is valid or not
     */
    public boolean validatePriority(int priority) {
        if (priority < 1 || priority > 10) return false;
        return true;
    }

    /**
     * This a method of lang.Object class, and it is used to compare two objects.<br>
     * To compare two objects that whether they are the same, it compares the values of both the object's attributes.
     * @param o other object to check.
     * @return True or False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriorityData that)) return false;
        return Arrays.equals(arrayOfPriority, that.arrayOfPriority);
    }
    /**
     * The hashcode() method returns the same hash value when called on two objects, which are equal according to the equals() method.<br>
     * And if the objects are unequal, it usually returns different hash values.
     * @return result value.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(arrayOfPriority);
    }



}
