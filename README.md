PuzzleView
==========

拼图控件
===================================
使用mask(蒙版)来实现将图片显示成任意形状，和拼图功能一致。
![github](https://raw.githubusercontent.com/lichuanzhi7909/PuzzleView/master/app/src/main/res/drawable-xhdpi/demo.png)

```java
    public class MentionSpanRenderer implements AwesomeTextHandler.ViewSpanRenderer, AwesomeTextHandler.ViewSpanClickListener {

        @Override
        public View getView(String text, Context context) {
            LayoutInflater inflater = LayoutInflater.from(context);
            TextView mentionView = inflater.inflate(R.layout.mention);
            mentionView.setText(text);
            return mentionView;
        }

        @Override
        public void onClick(String text, Context context) {
            Toast.makeText(context, "Hello " + text, Toast.LENGTH_SHORT).show();
        }
    }
```
