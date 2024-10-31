/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework;

import java.io.Serializable;

import org.springframework.aop.SpringProxy;

/**
 * Default {@link AopProxyFactory} implementation,
 * creating either a CGLIB proxy or a JDK dynamic proxy.
 *
 * <p>Creates a CGLIB proxy if one the following is true
 * for a given {@link AdvisedSupport} instance:
 * <ul>
 * <li>the "optimize" flag is set
 * <li>the "proxyTargetClass" flag is set
 * <li>no proxy interfaces have been specified
 * </ul>
 *
 * <p>Note that the CGLIB library classes have to be present on
 * the class path if an actual CGLIB proxy needs to be created.
 *
 * <p>In general, specify "proxyTargetClass" to enforce a CGLIB proxy,
 * or specify one or more interfaces to use a JDK dynamic proxy.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 12.03.2004
 * @see AdvisedSupport#setOptimize
 * @see AdvisedSupport#setProxyTargetClass
 * @see AdvisedSupport#setInterfaces
 */
@SuppressWarnings("serial")
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {


	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
		//这段代码用来判断选择那种创建代理对象的方式
		//config.optimize（） 是否对代理类的生成使用策略优化，其作用是和isProxyTargetClass是一样的，默认false
		//config.isProxyTargetClass 是否使用Cglib的方式创建代理对象，默认false
		//hasNoUserSuppliedProxyInterfaces目标类是否有接口存在，且只有一个接口的时候接口类型不是SpringProxy类型
		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			// 上面三个方法有一个为ture的，则进入
			// 从AdvisedSupport中获取目标类，类对象
			Class targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			//判断目标类是否是接口 如果目标类是接口的话，则还是使用JDK的方式生成代理对象
			//如果目标类是Proxy类型，则还是使用JDK的方式生成代理对象
			if (targetClass.isInterface()) {
				return new JdkDynamicAopProxy(config);
			}
			//配置了使用Cglib进行动态代理或者目标类没有接口，那么使用Cglib的方式创建代理对象
			return CglibProxyFactory.createCglibProxy(config);
		}
		else {
			//使用JDK的提供的代理方式生成代理对象
			return new JdkDynamicAopProxy(config);
		}
	}

	/**
	 * Determine whether the supplied {@link AdvisedSupport} has only the
	 * {@link org.springframework.aop.SpringProxy} interface specified
	 * (or no proxy interfaces specified at all).
	 */
	private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
		Class[] interfaces = config.getProxiedInterfaces();
		return (interfaces.length == 0 || (interfaces.length == 1 && SpringProxy.class.equals(interfaces[0])));
	}


	/**
	 * Inner factory class used to just introduce a CGLIB dependency
	 * when actually creating a CGLIB proxy.
	 */
	private static class CglibProxyFactory {

		public static AopProxy createCglibProxy(AdvisedSupport advisedSupport) {
			return new CglibAopProxy(advisedSupport);
		}
	}

}
