package com.springboot.AI_Code_Generator.repository;

import com.springboot.AI_Code_Generator.entity.Subscription;
import com.springboot.AI_Code_Generator.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    Optional<Object> findByStripeSubscriptionId(String gatewaySubscriptionId);

    boolean existsByStripeSubscriptionId(String subscriptionId);

    Optional<Subscription> findByUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> active);
}
