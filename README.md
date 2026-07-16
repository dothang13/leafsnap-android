Base Android Clean Architecture

### Config release

    File cấu hình cho app: app/src/release/res/values/configs.xml

### Module

    app: Module chính
    build-logic: Chứa các convention build logic cho module
    common: Extensions dùng chung cho các module
    data: Chứa business logic của app. Repository, Network, Database,v.v
    fox_ads: Google AdMob
    fox_purchase: In-App Billing
    fox_tracking: Tracking event sử dụng Adjust, Appsflyer, Firebase
    secrets: Mã hoá AES key
