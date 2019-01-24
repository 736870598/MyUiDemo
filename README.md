# MyUiDemo 
  android Ui小DEMO
  
  
### sample:
    
            list.add(new UiModel("防微信录制视频按钮效果", RecordBtnActivity.class));
            list.add(new UiModel("防即客APP点赞效果", PraiseViewActivity.class));
            list.add(new UiModel("RecycleView滑动定位", PosRecycleViewActivity.class));
            list.add(new UiModel("带删除和隐藏功能的editView", DelEditViewActivity.class));
            list.add(new UiModel("自定义流式布局", FlowLayoutActivity.class));
            list.add(new UiModel("item拖拽效果", DragActivity.class));
        
        
        
### ItemTouchHelper
         
下面说说ItemTouchHelper，ItemTouchHelper的功能就是他的名字，条目点击帮助类，可以实现点击或长按上下左右的拖动，用法：
    
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBack);
        itemTouchHelper.attachToRecyclerView(recycleView);
        
而我们要做的就是自定义一个callBack继承ItemTouchHelper.Callback，重写其中几个方法就可以了。

       public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {
       
          //判断是否需要监听动作
          @Override
          public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
              //监听上下拖动
              int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
              //监听左右拖动
              int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
              return makeMovementFlags(dragFlags, swipeFlags);
          }

          //上下移动item
          @Override
          public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
              return false;
          }

          //左右移动item    direction: 右->左  4    左->右  8
          @Override
          public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
          }

          @Override
          public boolean isLongPressDragEnabled() {
              //return true 表示允许长按拖动
              return true;
          }
       }