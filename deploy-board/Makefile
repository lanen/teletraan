build:
	v=1.0.0.dev0 ; \
	docker build -t deploy-board:$$v . ; \
	docker tag deploy-board:$$v deploy-board:latest ; \
	docker tag deploy-board:$$v registry.cn-shenzhen.aliyuncs.com/jiayi/deploy-board:$$v 
upload:
	v=1.0.0.dev0 ; \
	docker push registry.cn-shenzhen.aliyuncs.com/jiayi/deploy-board:$$v
