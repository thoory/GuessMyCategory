package com.thomasory.guessmycategory.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.thomasory.guessmycategory.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.thomasory.guessmycategory.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.thomasory.guessmycategory.domain.User.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Authority.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.User.class.getName() + ".authorities");
            createCache(cm, com.thomasory.guessmycategory.domain.Video.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Video.class.getName() + ".actors");
            createCache(cm, com.thomasory.guessmycategory.domain.Video.class.getName() + ".keywords");
            createCache(cm, com.thomasory.guessmycategory.domain.Video.class.getName() + ".tags");
            createCache(cm, com.thomasory.guessmycategory.domain.Actor.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Actor.class.getName() + ".sessions");
            createCache(cm, com.thomasory.guessmycategory.domain.Actor.class.getName() + ".answers");
            createCache(cm, com.thomasory.guessmycategory.domain.Actor.class.getName() + ".videos");
            createCache(cm, com.thomasory.guessmycategory.domain.OptionalImage.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.OptionalImage.class.getName() + ".sessions");
            createCache(cm, com.thomasory.guessmycategory.domain.OptionalImage.class.getName() + ".answers");
            createCache(cm, com.thomasory.guessmycategory.domain.Keyword.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Keyword.class.getName() + ".sessions");
            createCache(cm, com.thomasory.guessmycategory.domain.Keyword.class.getName() + ".answers");
            createCache(cm, com.thomasory.guessmycategory.domain.Keyword.class.getName() + ".videos");
            createCache(cm, com.thomasory.guessmycategory.domain.Tag.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Tag.class.getName() + ".sessions");
            createCache(cm, com.thomasory.guessmycategory.domain.Tag.class.getName() + ".answers");
            createCache(cm, com.thomasory.guessmycategory.domain.Tag.class.getName() + ".videos");
            createCache(cm, com.thomasory.guessmycategory.domain.Party.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Player.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Player.class.getName() + ".sessions");
            createCache(cm, com.thomasory.guessmycategory.domain.Score.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Answer.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Answer.class.getName() + ".sessions");
            createCache(cm, com.thomasory.guessmycategory.domain.Session.class.getName());
            createCache(cm, com.thomasory.guessmycategory.domain.Session.class.getName() + ".players");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
