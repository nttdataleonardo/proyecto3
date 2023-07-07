package com.bcp.proyecto3;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@Cacheable
public class CacheConfig extends CachingConfigurerSupport {

  @Value(value="${spring.redis.host}")
  private String host;

  @Value(value="${spring.redis.port}")
  private String port;

  @Value(value="${redis.timeout}")
  private String timeout;



  /*@Bean
  JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(Integer.valueOf(port));

    JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
    jedisClientConfiguration.connectTimeout(Duration.ofSeconds(Integer.valueOf(timeout)));// connection timeout

    JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
            jedisClientConfiguration.build());

    return jedisConFactory;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }*/

  /*@Bean
  ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
          ReactiveRedisConnectionFactory factory) {
    RedisSerializationContext<String, Object> serializationContext =
            RedisSerializationContext.<String, Object>newSerializationContext()
                    .key((RedisSerializationContext.SerializationPair<String>) RedisSerializationContext.string())
                    .value((RedisSerializationContext.SerializationPair<Object>) RedisSerializationContext.java())
                    .build();

    return new ReactiveRedisTemplate<>(factory, serializationContext);
  }*/

  /**
   * Del profesor en docs.
   */
  @Bean
  public RedisCacheConfiguration cacheConfiguration(){
    return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .disableCachingNullValues()
            .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
  }

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(){
    return (builder) -> builder
            .withCacheConfiguration("itemCache",
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
            .withCacheConfiguration("customerCache",
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));

  }
}