package com.hero.commons.test;

import org.junit.Test;

import com.hero.commons.common.resource.factory.Factory;
import com.hero.commons.common.resource.factory.SystemConfigFactory;
import com.hero.commons.test.model.ChannelService;

public class CommnsTest {
	@Test
	public void test1() {
		Factory factory = SystemConfigFactory.getSystemFactory(ChannelService.class).getItemFactory("testBank");
		ChannelService channelService = factory.getBean(ChannelService.class);
	}
}
