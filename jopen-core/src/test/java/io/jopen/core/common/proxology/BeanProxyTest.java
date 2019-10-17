package io.jopen.core.common.proxology;

import io.jopen.core.common.proxology.beans.BeanProxy;
import org.junit.Test;

/**
 * 测试函数式动态代理
 *
 * @author maxuefeng [m17793873123@163.com]
 */
public class BeanProxyTest {

	//
	public interface Person {

		//
		static Person create() {
			return create("", 0);
		}

		//
		static Person create(String name, int age) {
			
			Person person = BeanProxy.proxying(Person.class);
			person.setName(name);
			person.setAge(age);
			return person;
		}

		//
		String getName();

		//
		void setName(String name);

		//
		int getAge();

		//
		void setAge(int age);
	}


	@Test
	public void testInvoke() {

		//
		Person person = Person.create("kkkk", 23);

		//
		System.out.println(person);

		//
		System.out.println(person.getName());
	}

	public static void main(String[] args) {

		//
		Person person = Person.create("hh", 12);

		//
		System.out.println(person.getName());
	}


}
