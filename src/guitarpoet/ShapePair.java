package guitarpoet;

/**
 *
 * @author moshe
 */
public class ShapePair<A, B> {
    private final A stringNumber;
    private final B offset;

    public ShapePair(A strNum, B offset) {
    	super();
    	this.stringNumber = strNum;
    	this.offset = offset;
    }

    public int hashCode() {
    	int hashStringNumber = stringNumber != null ? stringNumber.hashCode() : 0;
    	int hashOffset = offset != null ? offset.hashCode() : 0;

    	return (hashStringNumber + hashOffset) * hashOffset + hashStringNumber;
    }

    public boolean equals(Object other) {
    	if (other instanceof ShapePair) {
    		ShapePair otherPair = (ShapePair) other;
    		return 
    		((  this.stringNumber == otherPair.stringNumber ||
    			( this.stringNumber != null && otherPair.stringNumber != null &&
    			  this.stringNumber.equals(otherPair.stringNumber))) &&
    		 (	this.offset == otherPair.offset ||
    			( this.offset != null && otherPair.offset != null &&
    			  this.offset.equals(otherPair.offset))) );
    	}

    	return false;
    }

    public String toString()
    { 
           return "(" + stringNumber + ", " + offset + ")"; 
    }

    public A getStringNumber() {
    	return stringNumber;
    }
    
    public B getOffset() {
    	return offset;
    }
}
