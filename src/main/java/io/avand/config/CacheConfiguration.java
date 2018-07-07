package io.avand.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.avand.domain.UserEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.UserEntity.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.avand.domain.UserEntity.class.getName() + ".companies", jcacheConfiguration);
            cm.createCache(io.avand.domain.UserEntity.class.getName() + ".talentPools", jcacheConfiguration);
            cm.createCache(io.avand.domain.UserEntity.class.getName() + ".invoices", jcacheConfiguration);
            cm.createCache(io.avand.domain.UserAuthorityEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.UserAuthorityEntity.class.getName() + ".userPermissions", jcacheConfiguration);
            cm.createCache(io.avand.domain.AuthorityEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.CandidateEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.CandidateEntity.class.getName() + ".feedbacks", jcacheConfiguration);
            cm.createCache(io.avand.domain.CandidateEntity.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(io.avand.domain.CandidateEntity.class.getName() + ".candidateSchedules", jcacheConfiguration);
            cm.createCache(io.avand.domain.CandidateEntity.class.getName() + ".candidateEvaluationCriteria", jcacheConfiguration);
            cm.createCache(io.avand.domain.JobEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.JobEntity.class.getName() + ".candidate", jcacheConfiguration);
            cm.createCache(io.avand.domain.CompanyEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.CompanyEntity.class.getName() + ".jobs", jcacheConfiguration);
            cm.createCache(io.avand.domain.CompanyEntity.class.getName() + ".evaluationCriteria", jcacheConfiguration);
            cm.createCache(io.avand.domain.CompanyEntity.class.getName() + ".companyPipelines", jcacheConfiguration);
            cm.createCache(io.avand.domain.InvoiceEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.InvoiceEntity.class.getName() + ".paymentTransactions", jcacheConfiguration);
            cm.createCache(io.avand.domain.PlanEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.AbstractAuditingEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.FileEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.CommentEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.FeedbackEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.UserPermissionEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.CandidateScheduleEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.PaymentTransactionEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.TalentPoolEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.EvaluationCriteriaEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.CompanyPipelineEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.CandidateEvaluationCriteriaEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.SubscriptionEntity.class.getName(), jcacheConfiguration);
            cm.createCache(io.avand.domain.SubscriptionHistoryEntity.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
