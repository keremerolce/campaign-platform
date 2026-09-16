# Campaign Platform

Bankacılık/fintech sektöründeki kampanya yönetim sistemlerinden esinlenerek geliştirilmiş, müşterileri harcama davranışlarına göre otomatik segmentlere ayıran ve segment değişikliklerini olay tabanlı (event-driven) mimariyle yayınlayan bir backend sistemi.

## Özellikler

- Otomatik Segmentasyon: Müşterinin aylık harcamasına göre uygun segmenti (Standart, Silver, Gold, Premium) otomatik belirler
- Event-Driven Mimari: Segment ataması gerçekleştiğinde Apache Kafka üzerinden event yayınlanır
- REST API: Müşteri, segment ve kampanya yönetimi için CRUD endpoint'leri
- CORS Desteği: React frontend ile sorunsuz entegrasyon

## Teknoloji Yığını

- Java 25 / Spring Boot 4.1.1
- Spring Data JPA (Hibernate)
- H2 Database (in-memory, geliştirme ortamı)
- Apache Kafka (Docker üzerinde çalıştırılıyor)
- Maven

## Mimari

[Client] → [Campaign Platform API :8082] → [H2 Database]
                    ↓
          [Kafka: segment-events topic]
                    ↓
         [Notification Service :8083]

Bu proje, ilişkili notification-service (https://github.com/keremerolce/notification-service) reposuyla birlikte çalışır — segment değişikliği event'lerini dinleyip işler.

## Kurulum ve Çalıştırma

Gereksinimler: JDK 25+, Docker Desktop, Maven (proje içindeki mvnw wrapper kullanılabilir)

Adımlar:
1. Kafka'yı Docker ile başlat: docker compose up -d
2. Uygulamayı çalıştır: ./mvnw spring-boot:run
3. Uygulama http://localhost:8082 üzerinde ayağa kalkar.

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
- [ ] AI destekli kişiselleştirilmiş kampanya önerisi (Notification Service üzerinden)
- [ ] PostgreSQL'e geçiş
- [ ] React dashboard entegrasyonu
