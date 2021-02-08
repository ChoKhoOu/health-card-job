FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

################################

FROM adoptopenjdk:11-jre-hotspot
MAINTAINER chokhoou <chokhoou@gmail.com>
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

# JVM_XMS and JVM_XMX configs deprecated for removal in halov1.4.4
ENV JVM_XMS="64m" \
    JVM_XMX="64m" \
    TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

ENTRYPOINT ["java", "-Xms${JVM_XMS}","-Xmx${JVM_XMX}", "org.springframework.boot.loader.JarLauncher"]