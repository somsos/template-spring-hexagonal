# README temp

## Building process

```bash
sudo docker run --rm \
  -v /root/.m2:/root/.m2 \
  -v ./:/app \
  -w /app \
  maven:3.9.9-eclipse-temurin-11-alpine -B -DskipTests clean package
```