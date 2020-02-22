package com.example.quizbox;

public class Questions {
    String total,que,op1,op2,op3,ans;

    public Questions()
    {

    }
    public Questions(String total,String que,String op1,String op2,String op3,String ans)
    {
        this.total=total;
        this.que=que;
        this.op1=op1;
        this.op2=op2;
        this.op3=op3;
        this.ans=ans;
    }


    public String getTotal() {
        return total;
    }

    public void setTotal(String category) {
        this.total = total;
    }

    public String getQue() {
        return que;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
