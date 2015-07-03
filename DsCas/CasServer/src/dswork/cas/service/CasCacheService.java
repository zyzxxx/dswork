package dswork.cas.service;

import org.springframework.stereotype.Service;
import dswork.cas.service.cache.BaseMemcachedService;

@Service
public class CasCacheService extends BaseMemcachedService
{
	@Override
	protected String getPrefix()
	{
		return "cas.";
	}
}
