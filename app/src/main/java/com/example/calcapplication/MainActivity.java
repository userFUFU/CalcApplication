package com.example.calcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Button[] nums=new Button[10];
    Button pls,mns;
    Button mul,div;
    Button back;
    Button equ;
    Button cln;
    Button dot;
    TextView tv1,tv2;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        nums[0]=findViewById(R.id.btn0);
        nums[1]=findViewById(R.id.btn1);
        nums[2]=findViewById(R.id.btn2);
        nums[3]=findViewById(R.id.btn3);
        nums[4]=findViewById(R.id.btn4);
        nums[5]=findViewById(R.id.btn5);
        nums[6]=findViewById(R.id.btn6);
        nums[7]=findViewById(R.id.btn7);
        nums[8]=findViewById(R.id.btn8);
        nums[9]=findViewById(R.id.btn9);
        Calculate calculate = new Calculate();



        for(int i = 0; 10 > i; i=i+1){
            int finalI = i;
            nums[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flag==false){
                        tv1.setText(Integer.toString(finalI));
                        flag=true;
                    }
                    else tv1.setText(tv1.getText()+Integer.toString(finalI));
                }
            });
        }

        pls=findViewById(R.id.btn_pls);
        pls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=(String) tv1.getText();
                int len=str.length();
                if(str.charAt(len-1)>='0' && str.charAt(len-1)<='9')tv1.setText(str+"+");
            }
        });


        mns=findViewById(R.id.btn_mns);
        mns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=(String) tv1.getText();
                int len=str.length();
                if(str.charAt(len-1)>='0' && str.charAt(len-1)<='9')tv1.setText(str+"-");
            }
        });
        dot=findViewById(R.id.btn_pnt);
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=(String) tv1.getText();
                int len=str.length();
                if(str.charAt(len-1)>='0' && str.charAt(len-1)<='9')tv1.setText(str+".");
            }
        });

        mul=findViewById(R.id.btn_mul);
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=(String) tv1.getText();
                int len=str.length();
                if(str.charAt(len-1)>='0' && str.charAt(len-1)<='9')tv1.setText(str+"×");
            }
        });

        div=findViewById(R.id.btn_div);
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=(String) tv1.getText();
                int len=str.length();
                if(str.charAt(len-1)>='0' && str.charAt(len-1)<='9')tv1.setText(str+"÷");
            }
        });

        back=findViewById(R.id.btn_del);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str= (String) tv1.getText();
                int len=str.length();
                if(len!=0)str=str.substring(0,len-1);
                tv1.setText(str);
            }
        });


        cln=findViewById(R.id.btn_clr);
        cln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("0");
                tv2.setText("result!");
                flag=false;
            }
        });

        equ=findViewById(R.id.btn_equ);
        equ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=calculate.calc((String) tv1.getText());
                tv2.setText(str);
            }
        });

    }
    class Calculate {
        String[] opText = {"+", "-", "×", "÷", "."};

        private boolean isNum(char ch) {
            return (int) ch >= 48 && (int) ch <= 58;
        }

        private boolean isOperator(char ch) {
            for (String op : opText) {
                if (op.charAt(0) == ch) return true;
            }
            return false;
        }

        private boolean isDouble(String s) {
            try {
                Double.valueOf(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private boolean heightOperator(String o1, String o2) {
            if ((o1.equals("+") || o1.equals("-"))
                    && (o2.equals("×") || o2.equals("÷"))) {
                return false;  //false 先算右边
            } else return true; //左结合
        }

        private Double getCountResult(String oper, Double num1, Double num2) {
            if ("+".equals(oper))
                return num1 + num2;
            else if ("-".equals(oper))
                return num1 - num2;
            else if ("×".equals(oper))
                return num1 * num2;
            else if ("÷".equals(oper))
                return num1 / num2;
            else return 0.0;
        }


        private boolean isExpression(String str) {
            int flag = 0;
            if (!isNum(str.charAt((0))) || !isNum(str.charAt((str.length() - 1))))
                return false;

            for (int i = 0; i < str.length() - 1; i++) {
                char ch = str.charAt(i);
                char chb = str.charAt(i + 1);
                if ((ch == '.' && !isNum(chb)) || (!isNum(ch) && chb == '.')) {
                    return false;
                }
                if (isOperator(ch) && isOperator(chb)) {
                    return false;
                }
            }
            return true;
        }

        private List<String> resolveString(String str) {
            List<String> list = new ArrayList<String>();
            String temp = "";
            for (int i = 0; i < str.length(); i++) {
                final char ch = str.charAt(i);
                if (isNum(ch) || ch == '.') {
                    char c = str.charAt(i);
                    temp += c;
                } else if (isOperator(ch)) {
                    if (!temp.equals("")) {
                        list.add(temp);
                    }
                    list.add("" + ch);
                    temp = "";
                }
                if (i == str.length() - 1) {
                    list.add(temp);
                }
            }
            return list;
        }

        private List<String> nifix_to_post(List<String> list) { //转后缀
            Stack<String> stack = new Stack<String>();
            List<String> plist = new ArrayList<String>();
            for (String str : list) {
                if (isDouble(str)) {
                    plist.add(str);
                } else {
                    while (!stack.isEmpty() && heightOperator(stack.lastElement(), str)) {
                        plist.add(stack.pop());
                    }
                    stack.push(str);
                }
            }
            while (!stack.isEmpty()) {
                plist.add(stack.pop());
            }
            return plist;
        }


        private Double get_postfis_result(List<String> list) {
            Stack<String> stack = new Stack<String>();
            for (String str : list) {
                if (isDouble(str)) {
                    stack.push(str);
                } else if (isOperator(str.charAt((0)))) {
                    Double n2 = Double.parseDouble(stack.pop());
                    Double n1 = Double.parseDouble(stack.pop());
                    stack.push("" + getCountResult(str, n1, n2));
                }
                System.out.println(stack.toString());
            }
            return Double.valueOf(stack.pop());
        }

        String calc(String str) {
            if (!isExpression(str)) {
                return "Error!";
            }
            List<String> list = resolveString(str);
            list = nifix_to_post(list);
            return (String.format("%.6f", get_postfis_result(list)));
        }
    }
}