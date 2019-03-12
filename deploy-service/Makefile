build:
	docker run -it -v ~/.m2:/root/.m2 -v "$$PWD":/usr/src/mymaven -w /usr/src/mymaven maven mvn clean package -DskipTests
docker:
	name=deploy-service; \
	version=0.1-SNAPSHOT; \
	docker build -t $${name}:$${version} .; \
	docker tag $${name}:$${version} $${name}:latest
upload: docker
	name=deploy-service; \
	version=0.1-SNAPSHOT; \
	docker tag $${name}:$${version} registry.cn-shenzhen.aliyuncs.com/jiayi/deploy-service:$${version} ; \
	docker push registry.cn-shenzhen.aliyuncs.com/jiayi/deploy-service:$${version}
