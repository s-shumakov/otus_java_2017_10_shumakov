ДЗ-16: MessageServer

Cревер из ДЗ-15 разделить на три приложения:
* MessageServer
* Frontend
* DBServer

Запускать Frontend и DBServer из MessageServer.
Сделать MessageServer сокет-сервером, Frontend и DBServer клиентами.
Пересылать сообщения с Frontend на DBService через MessageServer. 
Запустить приложение с двумя фронтендами (на разных портах)* и двумя датабазными серверами.

\* если у вас запуск веб приложения в контейнере, то MessageServer может копировать root.war в контейнеры при старте