package io.avand.config;

import io.avand.domain.entity.jpa.*;
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
            cm.createCache(UserEntity.class.getName(), jcacheConfiguration);
            cm.createCache(UserEntity.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(UserEntity.class.getName() + ".companies", jcacheConfiguration);
            cm.createCache(UserEntity.class.getName() + ".talentPools", jcacheConfiguration);
            cm.createCache(UserEntity.class.getName() + ".invoices", jcacheConfiguration);
            cm.createCache(UserAuthorityEntity.class.getName(), jcacheConfiguration);
            cm.createCache(UserAuthorityEntity.class.getName() + ".userPermissions", jcacheConfiguration);
            cm.createCache(AuthorityEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CandidateEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CandidateEntity.class.getName() + ".feedbacks", jcacheConfiguration);
            cm.createCache(CandidateEntity.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(CandidateEntity.class.getName() + ".candidateSchedules", jcacheConfiguration);
            cm.createCache(CandidateEntity.class.getName() + ".candidateEvaluationCriteria", jcacheConfiguration);
            cm.createCache(CandidateEntity.class.getName() + ".candidateMessages", jcacheConfiguration);
            cm.createCache(JobEntity.class.getName(), jcacheConfiguration);
            cm.createCache(JobEntity.class.getName() + ".candidate", jcacheConfiguration);
            cm.createCache(CompanyEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CompanyEntity.class.getName() + ".jobs", jcacheConfiguration);
            cm.createCache(CompanyEntity.class.getName() + ".evaluationCriteria", jcacheConfiguration);
            cm.createCache(CompanyEntity.class.getName() + ".companyPipelines", jcacheConfiguration);
            cm.createCache(CompanyEntity.class.getName() + ".companyMembers", jcacheConfiguration);
            cm.createCache(InvoiceEntity.class.getName(), jcacheConfiguration);
            cm.createCache(InvoiceEntity.class.getName() + ".paymentTransactions", jcacheConfiguration);
            cm.createCache(PlanEntity.class.getName(), jcacheConfiguration);
            cm.createCache(AbstractAuditingEntity.class.getName(), jcacheConfiguration);
            cm.createCache(FileEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CommentEntity.class.getName(), jcacheConfiguration);
            cm.createCache(FeedbackEntity.class.getName(), jcacheConfiguration);
            cm.createCache(UserPermissionEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CandidateScheduleEntity.class.getName(), jcacheConfiguration);
            cm.createCache(PaymentTransactionEntity.class.getName(), jcacheConfiguration);
            cm.createCache(TalentPoolEntity.class.getName(), jcacheConfiguration);
            cm.createCache(EvaluationCriteriaEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CompanyPipelineEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CandidateEvaluationCriteriaEntity.class.getName(), jcacheConfiguration);
            cm.createCache(SubscriptionEntity.class.getName(), jcacheConfiguration);
            cm.createCache(SubscriptionHistoryEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CompanyMemberEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CandidateMessageEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CandidateMessageEntity.class.getName()+".child", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
