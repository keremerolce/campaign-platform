# Campaign Platform

Bankacılık/fintech sektöründeki kampanya yönetim sistemlerinden esinlenerek geliştirilmiş, müşterileri harcama davranışlarına göre otomatik segmentlere ayıran ve segment değişikliklerini olay tabanlı (event-driven) mimariyle yayınlayan bir backend sistemi.

## Özellikler

- Otomatik Segmentasyon: Müşterinin aylık harcamasına göre uygun segmenti (Standart, Silver, Gold, Premium) otomatik belirler
- Event-Driven Mimari: Segment ataması gerçekleştiğinde Apache Kafka üzerinden event yayınlanır
- REST API: Müşteri, segment ve kampanya yönetimi için CRUD endpoint'leri
- CORS Desteği: React frontend ile sorunsuz entegrasyon
- Versiyonlu Şema Yönetimi: Flyway ile kontrollü, izlenebilir veritabanı migration'ları

## Teknoloji Yığını

- Java 25 / Spring Boot 4.1.1
- Spring Data JPA (Hibernate)
- PostgreSQL 18 (kalıcı veritabanı)
- Flyway (versiyonlu şema migration'ları)
- Apache Kafka (Docker üzerinde çalıştırılıyor)
- Maven

## Mimari

[Client] → [Campaign Platform API :8082] → [PostgreSQL 18]
↓
[Kafka: segment-events topic]
↓
[Notification Service :8083]

Bu proje, ilişkili notification-service (https://github.com/keremerolce/notification-service) reposuyla birlikte çalışır — segment değişikliği event'lerini dinleyip işler.

## Kurulum ve Çalıştırma

Gereksinimler: JDK 25+, Docker Desktop, PostgreSQL 18, Maven (proje içindeki mvnw wrapper kullanılabilir)

Adımlar:
1. PostgreSQL'de `campaign_platform` adında bir veritabanı oluştur
2. `src/main/resources/application-secrets.properties` dosyasını oluştur (git'e dahil değil) ve içine `spring.datasource.password=<şifren>` ekle
3. Kafka'yı Docker ile başlat: `docker compose up -d`
4. Uygulamayı çalıştır: `./mvnw spring-boot:run`
5. Uygulama ayağa kalkarken Flyway, `db/migration` altındaki migration dosyalarını otomatik uygular
6. Uygulama http://localhost:8082 üzerinde ayağa kalkar

## API Endpoint'leri

GET /api/customers - Tüm müşterileri listeler
GET /api/customers/{id} - Belirli bir müşteriyi getirir
POST /api/customers - Yeni müşteri oluşturur, otomatik segment ataması yapar ve Kafka'ya event yayınlar

Örnek İstek (POST /api/customers):
{
"fullName": "Ahmet Yılmaz",
"monthlySpending": 6000.0
}

Örnek Yanıt:
{
"id": 1,
"fullName": "Ahmet Yılmaz",
"monthlySpending": 6000.0,
"segment": {
"id": 3,
"name": "Gold",
"minSpending": 5000.0
}
}

## Segment Eşikleri

Standart: 0 TL
Silver: 1.000 TL
Gold: 5.000 TL
Premium: 15.000 TL

## Yol Haritası

- [x] Otomatik segmentasyon mantığı
- [x] Kafka event yayınlama
- [x] PostgreSQL 18'e geçiş (Flyway ile versiyonlu migration)
- [ ] AI destekli kişiselleştirilmiş kampanya önerisi (Notification Service üzerinden)
- [ ] React dashboard entegrasyonu