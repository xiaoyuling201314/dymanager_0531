style.min.css删除以下样式
.header .user-info:hover .logout {
	display: block
}

.header .user-info:hover .info .arrow {
	-webkit-transform: rotate(180deg);
	-ms-transform: rotate(180deg);
	-o-transform: rotate(180deg);
	transform: rotate(180deg)
}
2017-2-27
维修记录表新增sapNo字段，解决历史出货记录的返修处理问题