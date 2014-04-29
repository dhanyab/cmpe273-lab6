package edu.sjsu.cmpe.cache.client;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

import java.util.List;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cacheA = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cacheB = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cacheC = new DistributedCacheService(
                "http://localhost:3002");
//        cacheA.put(1, "foo");
//        System.out.println("put(1 => foo)");
//
//        String value = cacheA.get(1);
//        System.out.println("get(1) => " + value);
//
//        System.out.println("Existing Cache Client...");
        
//        cacheB.put(4,"bar");
//        System.out.println(cacheB.get(4));
        
        List<CacheServiceInterface> servers = Lists.newArrayList(cacheA, cacheB, cacheC);
        char c = 'a';
        System.out.println("PUT: ");

        for (int i = 1; i <=10; i ++)
        {
        int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), servers.size());
        servers.get(bucket).put(i,Character.toString(c));
        System.out.println(" "+i+"=>"+c );
        c++;
        
        }
        System.out.println("GET: ");
        for(int i = 1; i<=10; i++ )
        {
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), servers.size());
        	System.out.println(" "+i+ "=>"+servers.get(bucket).get(i));
//        bucket = Hashing.consistentHash(Hashing.md5().hashString("dhanya"), servers.size());
//        System.out.println("Second time routed to: " + servers.get(bucket));
        }
        
        System.out.println("Existing Cache Client...");
    }

}
