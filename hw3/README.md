# Домашнее задание 3


1)	Параметризовать и реализовать хранилище всех банковских сущностей приложения

2)	Извлечь из класса Account интерфейс Account с методами и balanceOn и addEntry и переименовать класс Account в DebitCard. Реализовать дополнительный класс BonusAccount implements Account. Реализовать накопление бонусных баллов при при списании денег со счета на счет (метод withdraw(double amount, Account account)) в объеме заданного при инициализации BonusAccount процента.

3)	Реализовать в классе AnalyticsManager следующий метод

public double overallBalanceOfAccounts(List accounts);

public Set uniqueKeysOf(List accounts, KeyExtractor extractor);

public List accountsRangeFrom(List accounts, Account minAccount, Comparator comparator);
 
