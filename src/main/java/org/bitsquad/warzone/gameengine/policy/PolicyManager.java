package org.bitsquad.warzone.gameengine.policy;

import org.bitsquad.warzone.order.Order;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PolicyManager {
    List<Policy> d_policies;

    public PolicyManager() {
        d_policies = new LinkedList<>();
    }

    public void addPolicy(Policy p_policy){
        this.d_policies.add(p_policy);
    }

    public void clearPolicies(){
        d_policies = new LinkedList<>();
    }

    public boolean checkPolicies(Order p_order){
        Iterator<Policy> l_policyIterator = this.d_policies.iterator();
        while(l_policyIterator.hasNext()){
            Policy l_currentPolicy = l_policyIterator.next();
            if(!l_currentPolicy.check(p_order)) return false;
        }
        return true;
    }
}
