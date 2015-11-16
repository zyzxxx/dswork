package dswork.sso.service;

import org.springframework.stereotype.Service;

import dswork.sso.service.cache.BaseMemcachedService;

@Service
public class AuthCacheService extends BaseMemcachedService
{
	@Override
	protected String getPrefix()
	{
		return "cas.";
	}
}
