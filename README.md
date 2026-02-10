# Spring PetClinic サンプルアプリケーション [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)[![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/gradle-build.yml)

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/spring-projects/spring-petclinic) [![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=7517918)

## ダイアグラムでSpring Petclinicアプリケーションを理解する

[プレゼンテーションはこちら](https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application)

## Petclinicをローカルで実行する

Spring Petclinicは、[Maven](https://spring.io/guides/gs/maven/)または[Gradle](https://spring.io/guides/gs/gradle/)を使用してビルドされた[Spring Boot](https://spring.io/guides/gs/spring-boot)アプリケーションです。jarファイルをビルドしてコマンドラインから実行できます（Java 17以降で動作します）：

```bash
git clone https://github.com/spring-projects/spring-petclinic.git
cd spring-petclinic
./mvnw package
java -jar target/*.jar
```

（Windowsの場合、またはシェルがグロブを展開しない場合は、コマンドラインの最後でJARファイル名を明示的に指定する必要があります。）

その後、<http://localhost:8080/>でPetclinicにアクセスできます。

<img width="1042" alt="petclinic-screenshot" src="https://cloud.githubusercontent.com/assets/838318/19727082/2aee6d6c-9b8e-11e6-81fe-e889a5ddfded.png">

または、Spring Boot Mavenプラグインを使用してMavenから直接実行することもできます。この方法では、プロジェクトに加えた変更をすぐに反映できます（Javaソースファイルの変更にはコンパイルが必要です - ほとんどの人はIDEを使用します）：

```bash
./mvnw spring-boot:run
```

> 注意: Gradleを使用する場合は、`./gradlew build`でアプリをビルドし、`build/libs`にあるjarファイルを探してください。

## コンテナのビルド

このプロジェクトには`Dockerfile`はありません。Spring Bootビルドプラグインを使用してコンテナイメージをビルドできます（dockerデーモンがある場合）：

```bash
./mvnw spring-boot:build-image
```

## Spring Petclinicのバグや改善提案を見つけた場合

課題トラッカーは[こちら](https://github.com/spring-projects/spring-petclinic/issues)で利用可能です。

## データベース設定

デフォルト設定では、Petclinicは起動時にデータが投入されるインメモリデータベース（H2）を使用します。h2コンソールは`http://localhost:8080/h2-console`で公開されており、`jdbc:h2:mem:<uuid>`URLを使用してデータベースの内容を確認できます。UUIDは起動時にコンソールに出力されます。

永続的なデータベース設定が必要な場合は、MySQLとPostgreSQL用の同様のセットアップが提供されています。データベースの種類が変わるたびに、アプリは異なるプロファイルで実行する必要があります：MySQLの場合は`spring.profiles.active=mysql`、PostgreSQLの場合は`spring.profiles.active=postgres`です。アクティブなプロファイルの設定方法の詳細については、[Spring Bootドキュメント](https://docs.spring.io/spring-boot/how-to/properties-and-configuration.html#howto.properties-and-configuration.set-active-spring-profiles)を参照してください。

お使いのOSで動作するインストーラーを使用してMySQLまたはPostgreSQLをローカルで起動するか、dockerを使用できます：

```bash
docker run -e MYSQL_USER=petclinic -e MYSQL_PASSWORD=petclinic -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=petclinic -p 3306:3306 mysql:9.2
```

or

```bash
docker run -e POSTGRES_USER=petclinic -e POSTGRES_PASSWORD=petclinic -e POSTGRES_DB=petclinic -p 5432:5432 postgres:17.5
```

[MySQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/mysql/petclinic_db_setup_mysql.txt)と[PostgreSQL](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources/db/postgres/petclinic_db_setup_postgres.txt)の詳細なドキュメントが提供されています。

通常の`docker`の代わりに、提供されている`docker-compose.yml`ファイルを使用してデータベースコンテナを起動することもできます。各サービスはSpringプロファイルにちなんで名付けられています：

```bash
docker compose up mysql
```

or

```bash
docker compose up postgres
```

## テストアプリケーション

開発時には、`PetClinicIntegrationTests`（デフォルトのH2データベースを使用し、Spring Boot Devtoolsも追加）、`MySqlTestApplication`、`PostgresIntegrationTests`の`main()`メソッドとして設定されたテストアプリケーションを使用することをお勧めします。これらは、IDEでアプリを実行して迅速なフィードバックを得られるように設定されており、同じクラスを各データベースに対する統合テストとして実行することもできます。MySQL統合テストはTestcontainersを使用してDockerコンテナでデータベースを起動し、PostgresテストはDocker Composeを使用して同じことを行います。

## CSSのコンパイル

`src/main/resources/static/resources/css`に`petclinic.css`があります。これは`petclinic.scss`ソースから生成され、[Bootstrap](https://getbootstrap.com/)ライブラリと組み合わされています。`scss`を変更したり、Bootstrapをアップグレードしたりする場合は、Mavenプロファイル「css」を使用してCSSリソースを再コンパイルする必要があります。つまり、`./mvnw package -P css`です。CSSをコンパイルするためのGradleビルドプロファイルはありません。

## IDEでPetclinicを操作する

### 前提条件

システムに以下のものがインストールされている必要があります：

- Java 17以降（完全なJDK、JREではありません）
- [Gitコマンドラインツール](https://help.github.com/articles/set-up-git)
- お好みのIDE
  - m2eプラグイン付きのEclipse。注意：m2eが利用可能な場合、`ヘルプ -> バージョン情報`ダイアログにm2アイコンがあります。m2eがない場合は、[こちら](https://www.eclipse.org/m2e/)のインストールプロセスに従ってください
  - [Spring Tools Suite](https://spring.io/tools)（STS）
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  - [VS Code](https://code.visualstudio.com)

### 手順

1. コマンドラインで次を実行します：

    ```bash
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

1. Inside Eclipse or STS:

    `ファイル -> インポート -> Maven -> 既存のMavenプロジェクト`でプロジェクトを開き、クローンしたリポジトリのルートディレクトリを選択します。

    次に、コマンドライン`./mvnw generate-resources`でビルドするか、Eclipseランチャー（プロジェクトを右クリックして`実行 -> Maven install`）を使用してCSSを生成します。アプリケーションのmainメソッドを右クリックして`実行 -> Javaアプリケーション`を選択して実行します。

1. IntelliJ IDEA内：

    メインメニューで`ファイル -> 開く`を選択し、Petclinicの[pom.xml](pom.xml)を選択します。`開く`ボタンをクリックします。

    - CSSファイルはMavenビルドから生成されます。コマンドライン`./mvnw generate-resources`でビルドするか、`spring-petclinic`プロジェクトを右クリックして`Maven -> ソースの生成とフォルダの更新`を選択します。

    - 最新のUltimateバージョンを使用している場合は、`PetClinicApplication`という名前の実行構成が作成されているはずです。それ以外の場合は、`PetClinicApplication`メインクラスを右クリックして`'PetClinicApplication'を実行`を選択してアプリケーションを実行します。

1. Petclinicに移動します

    ブラウザで[http://localhost:8080](http://localhost:8080)にアクセスします。

## 特定のものを探していますか？

|Spring Boot設定 | クラスまたはJavaプロパティファイル  |
|--------------------------|---|
|メインクラス | [PetClinicApplication](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
|プロパティファイル | [application.properties](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/resources) |
|キャッシング | [CacheConfiguration](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## 興味深いSpring Petclinicのブランチとフォーク

[spring-projects](https://github.com/spring-projects/spring-petclinic) GitHub組織のSpring Petclinic「main」ブランチは、Spring BootとThymeleafに基づく「正規」の実装です。GitHub組織[spring-petclinic](https://github.com/spring-petclinic)には[かなりの数のフォーク](https://spring-petclinic.github.io/docs/forks.html)があります。異なる技術スタックを使用してPet Clinicを実装することに興味がある場合は、そちらのコミュニティにご参加ください。

## 他のオープンソースプロジェクトとの相互作用

Spring Petclinicアプリケーションに取り組む最良の部分の1つは、多くのオープンソースプロジェクトと直接連携する機会があることです。Spring、Spring Data、Bean Validation、さらにはEclipseなど、さまざまなトピックでバグを発見したり、改善を提案したりしました。多くの場合、それらは数日で修正/実装されています。
以下はそのリストです：

| 名前 | 課題 |
|------|-------|
| Spring JDBC: NamedParameterJdbcTemplateの使用を簡素化 | [SPR-10256](https://github.com/spring-projects/spring-framework/issues/14889)および[SPR-10257](https://github.com/spring-projects/spring-framework/issues/14890) |
| Bean Validation / Hibernate Validator: Maven依存関係と下位互換性を簡素化 |[HV-790](https://hibernate.atlassian.net/browse/HV-790)および[HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: JPQLクエリを操作する際により多くの柔軟性を提供 | [DATAJPA-292](https://github.com/spring-projects/spring-data-jpa/issues/704) |

## 貢献

[課題トラッカー](https://github.com/spring-projects/spring-petclinic/issues)は、バグレポート、機能リクエスト、プルリクエストの提出のための推奨チャネルです。

プルリクエストの場合、一般的なテキストエディタで簡単に使用できるように、エディタ設定が[editor config](.editorconfig)で利用可能です。詳細とプラグインのダウンロードについては<https://editorconfig.org>をご覧ください。すべてのコミットには、貢献者がDeveloper Certificate of Originに同意することを示すために、各コミットメッセージの最後に__Signed-off-by__トレーラーを含める必要があります。
詳細については、ブログ記事[Hello DCO, Goodbye CLA: Simplifying Contributions to Spring](https://spring.io/blog/2025/01/06/hello-dco-goodbye-cla-simplifying-contributions-to-spring)を参照してください。

## ライセンス

Spring PetClinicサンプルアプリケーションは、[Apache License](https://www.apache.org/licenses/LICENSE-2.0)のバージョン2.0の下でリリースされています。
