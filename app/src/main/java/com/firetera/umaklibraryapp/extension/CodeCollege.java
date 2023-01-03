package com.firetera.umaklibraryapp.extension;

public class CodeCollege {

    String college;

    public CodeCollege(String college) {
        if(college.equals("College of Computer Information Science")){
            this.college = "CCIS";
        }else if(college.equals("College of Arts and Letters")){
            this.college = "CAL";
        }
    }

    public String getCollege() {
        return college;
    }
}
