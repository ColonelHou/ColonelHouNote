// remap $ to $
(function($){


/* trigger when page is ready */

$(document).ready(function(){
  //nav下拉菜单的处理
  var $ulParent= $('.menu>ul').find('li:has(ul:not(:empty))');
  $ulParent.addClass("sub").bind('mouseover',function(){$(this).addClass('hover');}).bind('mouseleave',function(){$(this).removeClass('hover');});

  $ulParent.hover(
  function(){
      $(this).children('ul').clearQueue().slideDown('fast');
  },function(){
      $(this).children('ul').clearQueue().slideUp('fast', function(){
        $(this).css('height', '');
      });
  });
  $('.menu>ul>li>ul li:last-child').addClass('last');
  $ulParent.children('a').addClass('sub');
});
  
})(window.jQuery);

/* optional triggers

$(window).load(function() {
	
});

$(window).resize(function() {
	
});

*/