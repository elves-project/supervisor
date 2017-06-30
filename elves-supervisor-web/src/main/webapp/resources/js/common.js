/**
 * TP_RBAC 通用脚本
 * 
 * 作者：唐卓琦(tangzhuoqi@gyyx.cn)
 * 依赖：JQuery 1.11.3, Bootstrap 3.3.4, JS-Cookie 1.5.1
 * 浏览器兼容性：IE8+, Chrome, Firefox
 * 
 */

var modules = {
	layoutPage: function() {
		$(document).ready(function() {
			// 边栏导航逻辑
			var nav = $('#js-l-sidebar-nav');

			// 初始化已展开的二级导航菜单高度
			// 此处设计是由于 CSS3 Transition 不支持属性值为 Auto 的渲染过渡操作，所以将需要过渡的属性值具体化。
			// 标记为 // Transition fix 的语句均与该问题有关。
			nav.find('.l-sidebar-nav-second:not(.s-closed)').each(function() {
				var secondMenu = $(this).find('.l-sidebar-nav-second-menu');
				secondMenu.height(secondMenu.height());
			});

			nav.delegate('.l-sidebar-nav-top-title', 'click', function() {
				nav.children('.l-sidebar-nav-top.s-active').removeClass('s-active');
				$(this).parent().addClass('s-active');
			}).delegate('.l-sidebar-nav-second-title', 'click', function() {
				var secondNav = $(this).parent();
				var secondMenu = secondNav.children('.l-sidebar-nav-second-menu');
				var secondMenuInner = secondMenu.children('.l-sidebar-nav-second-menu-inner');
				if (secondNav.hasClass('s-closed')) {
					// Transition fix
					secondMenu.height(secondMenuInner.height());
					secondNav.removeClass('s-closed');
				} else {
					// Transition fix
					secondMenu.height(0);
					secondNav.addClass('s-closed');
				}
			});

			// 边栏开关逻辑
			var body = $(document.body);
			$('#js-l-sidebar-switch').click(function() {
				if (body.hasClass('s-sidebar-closed')) {
					body.removeClass('s-sidebar-closed');
					Cookies.remove('s-sidebar-closed', {path: '/'});
				} else {
					body.addClass('s-sidebar-closed');
					Cookies.set('s-sidebar-closed', '1', {path: '/'});
				}
			});
		});
	}
};
