// This is the class use to store all the local variables 
// within the recursive version of the fibonacci method
class ProgramFrame {

    int n;
    int firstFib;
    int secondFib;
    int PC;
   
    // Constructor
    public ProgramFrame(int myN, int myFirstFib, int mySecondFib, int myPC) {
		n = myN;
		firstFib = myFirstFib;
		secondFib = mySecondFib;
		PC = myPC;
    }

    // returns a String describing the content of the object
    public String toString() {
		return "ProgramFrame: n = " + n + " firstFib = " + firstFib + " secondFib = " + secondFib + " PC = " + PC;
    }

}
