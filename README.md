🔗 Shrinkoo — URL Shortener with Analytics

Shrinkoo is a Java-based URL shortener designed to demonstrate core system design fundamentals such as identifier resolution, event tracking, query optimization, and time-series analytics.
The project focuses on how real-world URL shortening systems are designed and scaled, not just shortening links.

🚀 Key Features

🔗 Short URL generation with collision-safe identifiers

📊 Global analytics dashboard (total clicks over time)

📈 Per-URL analytics (clicks grouped by date)

🧠 Event-based click tracking

🔐 Authenticated user-based URL ownership

⚡ Optimized read paths for analytics queries

🧠 Why URL Shortening?

URL shortening is one of the most common and fundamental system design problems asked in backend interviews because it touches:

High read/write traffic patterns

Identifier generation & resolution

Event tracking

Data modeling for analytics

Query optimization & indexing

Scalability trade-offs

Shrinkoo was built specifically to apply these concepts hands-on.

🏗️ System Design Overview
🔹 Core Entities

User

UrlMapping

id

shortUrl

originalUrl

clickCount

EventClick

id

clickDate

urlMappingId

🔹 Click Tracking Flow

User opens a short URL

Backend resolves shortUrl → originalUrl

clickCount is incremented on UrlMapping

A new EventClick record is inserted

User is redirected to the original URL

This dual approach enables:

Fast counters for UI display

Accurate, queryable analytics over time

📊 Analytics Design
1️⃣ Global Analytics (Dashboard)

Shows total clicks per day

Aggregates all EventClick records

Used for top-level engagement insights

Query concept:

SELECT DATE(click_date), COUNT(*)
FROM event_click
GROUP BY DATE(click_date);

2️⃣ Per-URL Analytics (URL List)

Shows clicks per day for a specific short URL

Filters by url_mapping_id

Grouped by click_date

Query concept:

SELECT DATE(ec.click_date), COUNT(*)
FROM event_click ec
JOIN url_mapping um ON ec.url_mapping_id = um.id
WHERE um.short_url = :shortUrl
GROUP BY DATE(ec.click_date);


This separation ensures analytics remain accurate, scalable, and query-efficient.

🧩 Analytics Data Modeling (Important)

Each click is stored as an event (append-only)

Enables:

Time-series analysis

Future extensions (geo, device, referrer)

Avoids expensive recalculations on every request

This mirrors how production systems like Bitly and TinyURL model analytics.

⚡ Performance & Optimization Notes

📌 Indexes on:

url_mapping.short_url

event_click.url_mapping_id

event_click.click_date

<img width="1356" height="976" alt="image" src="https://github.com/user-attachments/assets/0924cd61-cdbd-41ac-b83a-2928146e3931" />



📌 Read-heavy paths optimized for:

O(1) URL resolution

O(log n) analytics queries

📌 Event-based design avoids write contention

🔐 Security & Access Control

URLs are scoped per authenticated user

Analytics endpoints validate ownership

Prevents cross-user data access

🧪 Common Debugging Insights (Learnings)

Global analytics can work even when per-entity analytics fail

Most analytics bugs come from:

Incorrect identifier resolution

Date range filtering

Missing foreign key relationships

UI rendering issues are often data-layer issues, not frontend bugs

🛠️ Tech Stack (High-Level)

Backend: Java (Spring Boot)

Database: Neon (PostgreSQL)

Frontend: React

Charts: Time-series based visualizations

Auth: Token-based authentication

(Tech stack intentionally kept simple to focus on system design.)

🎯 Interview-Ready Takeaways

Demonstrates real-world event-driven analytics

Shows clear separation of concerns:

URL resolution

Click tracking

Analytics aggregation

Covers core backend concepts expected at SDE-1 / SDE-2 level

📌 Future Enhancements

Caching layer for hot URLs

Rate limiting on short URL resolution

Geo & device-based analytics

Asynchronous event ingestion

Partitioned analytics tables for scale

📄 Project Status

✅ Core system design complete
✅ Analytics pipeline implemented
✅ Suitable for interviews, demos, and learning


