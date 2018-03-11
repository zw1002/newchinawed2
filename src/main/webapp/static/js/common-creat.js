//把top按钮放入iframe里
$(function () {
    $(".btn-group-sm").append("<a href='##' id='top_back'></a>");//把元素放进iframe标签里面
    $(".style_body:first").append("<a href='#top_back' type='button' class='top_back btn btn-default'><i class='fa fa-angle-up'></i></a>");//把元素放进iframe标签里面
    $(".modal-content").draggable({cursor: "move"});//为模态对话框添加拖拽
});
