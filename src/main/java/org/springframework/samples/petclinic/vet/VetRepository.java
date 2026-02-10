/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * <code>Vet</code>ドメインオブジェクト用のRepositoryクラス。すべてのメソッド名はSpring Dataの
 * 命名規則に準拠しているため、このインターフェースはSpring Data用に簡単に拡張できます。
 * 参照：
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface VetRepository extends Repository<Vet, Integer> {

	/**
	 * データストアからすべての<code>Vet</code>を取得します。
	 * @return <code>Vet</code>の<code>コレクション</code>
	 */
	@Transactional(readOnly = true)
	@Cacheable("vets")
	Collection<Vet> findAll() throws DataAccessException;

	/**
	 * データストアからすべての<code>Vet</code>をページ単位で取得します
	 * @param pageable ページング情報
	 * @return Vetのページ
	 * @throws DataAccessException データアクセス例外
	 */
	@Transactional(readOnly = true)
	@Cacheable("vets")
	Page<Vet> findAll(Pageable pageable) throws DataAccessException;

}
