USE [master]
GO
/****** Object:  Database [DarbDB]    Script Date: 13/04/2018 8:14:23 PM ******/
CREATE DATABASE [DarbDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DarbDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\DarbDB.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'DarbDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\DarbDB_1.ldf' , SIZE = 4672KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [DarbDB] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DarbDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DarbDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DarbDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DarbDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DarbDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DarbDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [DarbDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DarbDB] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [DarbDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DarbDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DarbDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DarbDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DarbDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DarbDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DarbDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DarbDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DarbDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [DarbDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DarbDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DarbDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DarbDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DarbDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DarbDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DarbDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DarbDB] SET RECOVERY FULL 
GO
ALTER DATABASE [DarbDB] SET  MULTI_USER 
GO
ALTER DATABASE [DarbDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DarbDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DarbDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DarbDB] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [DarbDB]
GO
/****** Object:  User [GS1\Administrator]    Script Date: 13/04/2018 8:14:23 PM ******/
CREATE USER [GS1\Administrator] FOR LOGIN [GS1\Administrator] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [GS1\Administrator]
GO
ALTER ROLE [db_securityadmin] ADD MEMBER [GS1\Administrator]
GO
ALTER ROLE [db_backupoperator] ADD MEMBER [GS1\Administrator]
GO
ALTER ROLE [db_datareader] ADD MEMBER [GS1\Administrator]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [GS1\Administrator]
GO
/****** Object:  StoredProcedure [dbo].[InsertProviderServices]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[InsertProviderServices] 
@Id as int, 
@ProviderId as int, 
@SubServiceId as int, 
@InCityCharge as Nvarchar(50),
@OutCityCharge as Nvarchar(50),
@Flag as int
AS
BEGIN
if(@Flag=0)
if exists(Select * from ProviderServices where (Id=@Id and SubServiceId=@SubServiceId) or Id=@Id)
UPDATE    ProviderServices
SET              ProviderId =@ProviderId, SubServiceId =@SubServiceId, 
InCityCharge =@InCityCharge, OutCityCharge =@OutCityCharge where Id=@Id or SubServiceId=@SubServiceId
else
INSERT INTO ProviderServices
                      (ProviderId, SubServiceId, InCityCharge, OutCityCharge)
VALUES     (@ProviderId, @SubServiceId, @InCityCharge, @OutCityCharge)
else
update ProviderServices set IsActive=0 where Id=@Id
END

GO
/****** Object:  StoredProcedure [dbo].[InsertServiceProvider]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[InsertServiceProvider]
(
	@ProviderId				as int, 
	@ProvicerName			as nvarchar(50),
	@Location				as nvarchar(50),
	@Email					as nvarchar(50), 
	@MobileNo as nvarchar(50), 
	@Longitude as nvarchar(50), 
	@Latitude as nvarchar(50),
	@Male as bit,
	@Female as bit,
	@LinkUserID as int
)
AS
BEGIN
if exists(Select * from ServiceProvider where ProviderId=@ProviderId)

UPDATE    ServiceProvider
SET              ProvicerName =@ProvicerName, Location =@Location, Email =@Email, MobileNo =@MobileNo,
 Longitude =@Longitude, Latitude =@Latitude, LinkUserID =@LinkUserID where ProviderId=@ProviderId
 
else

INSERT INTO ServiceProvider
                      (ProvicerName, Location, Email, MobileNo, Longitude, Latitude,Male,Female, Active, LinkUserID)
VALUES     (@ProvicerName, @Location, @Email, @MobileNo, @Longitude, @Latitude, @Male,@Female,1, @LinkUserID)


END

GO
/****** Object:  StoredProcedure [dbo].[InsertTransactions]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[InsertTransactions]
@UserId as int, 
@SubServiceId as int, 
@ProvicerId as int, 
@ChargeAmount as Nvarchar(50),
@Longitude as Nvarchar(50),
@Latitude as Nvarchar(50),
@PriorityId as int, 
@RequestStatusId as int,
@PaymentMethodsID as int,
@ID int output
AS
BEGIN
INSERT INTO Transactions
                      (UserId, SubServiceId, ProvicerId, ChargeAmount, Longitude, Latitude,
                       PriorityId, RequestStatusId, DateAdded, PaymentMethodsID)
                       
VALUES     (@UserId, @SubServiceId, @ProvicerId, @ChargeAmount, @Longitude, @Latitude,
                       @PriorityId, @RequestStatusId, GETDATE(), @PaymentMethodsID)
select scope_identity()
END

GO
/****** Object:  StoredProcedure [dbo].[InsertUsers]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[InsertUsers]

@UserId as int,
@UserName as nvarchar(50), 
@FullName as nvarchar(50), 
@Password as nvarchar(50), 
@MobileNo as nvarchar(50), 
@Email as nvarchar(50), 
@UserTypeId as int,
@Balance as nvarchar(50),
@Active as bit,
@ID int output
AS
BEGIN
INSERT INTO Users
                      (UserName, FullName, Password, MobileNo, Email, UserTypeId, Balance, DateAdded, Active)
VALUES     (@UserName, @FullName, @Password, @MobileNo, @Email, @UserTypeId, @Balance, GETDATE(), @Active)
select scope_identity()
END

GO
/****** Object:  StoredProcedure [dbo].[SelectLogin]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--[SelectLogin]'22123','123'
CREATE PROCEDURE  [dbo].[SelectLogin]

@UserName as Nvarchar(50),
@Password as Nvarchar(50)

AS
BEGIN
	
SELECT  top 1   Users.UserId, Users.UserName, Users.FullName, 
Users.Password, Users.MobileNo, Users.Email, Users.UserTypeId, 
Users.Balance, Users.Active, isnull(ServiceProvider.ProviderId,0) as ProviderId
FROM         Users LEFT OUTER JOIN
                      ServiceProvider ON Users.UserId = ServiceProvider.LinkUserID
where Users.Active=1 and Users.UserName=@UserName and Users.Password=@Password

END

GO
/****** Object:  StoredProcedure [dbo].[SelectPaymentMethods]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectPaymentMethods]
AS
BEGIN

SELECT     PaymentID, PaymentName
FROM         PaymentMethods

END

GO
/****** Object:  StoredProcedure [dbo].[SelectPriority]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectPriority] 

AS
BEGIN
SELECT     PriorityId, PrioriyName, Priority
FROM         SerivcePriority
END

GO
/****** Object:  StoredProcedure [dbo].[SelectProviderServices]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectProviderServices]

@ProviderId as integer, 
@SubServiceId as integer

AS
BEGIN
SELECT        
		ProviderServices.Id, 
		ProviderServices.ProviderId, 
		ProviderServices.SubServiceId, 
		SubServices.SubServiceName, 
		ProviderServices.InCityCharge, 
		ProviderServices.OutCityCharge, 
		SubServices.ServiceId
FROM ProviderServices INNER JOIN SubServices 
	ON ProviderServices.SubServiceId = SubServices.SubServiceId INNER JOIN ServiceProvider 
	ON ProviderServices.ProviderId = ServiceProvider.ProviderId
WHERE        (ProviderServices.ProviderId = @ProviderId or ServiceProvider.LinkUserID=@ProviderId) AND
 (ProviderServices.SubServiceId = @SubServiceId) AND (ProviderServices.IsActive = 1)
END

GO
/****** Object:  StoredProcedure [dbo].[SelectProviderServicesByProvicerId]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

create PROCEDURE [dbo].[SelectProviderServicesByProvicerId]
@ProviderId as int
AS
BEGIN

SELECT     
	
	ProviderServices.Id, 
	ProviderServices.ProviderId, 
	ProviderServices.SubServiceId, 
	SubServices.SubServiceName, 
	ProviderServices.InCityCharge, 
	ProviderServices.OutCityCharge
	
FROM ProviderServices INNER JOIN SubServices 
 ON ProviderServices.SubServiceId = SubServices.SubServiceId
where ProviderServices.ProviderId=@ProviderId
END

GO
/****** Object:  StoredProcedure [dbo].[SelectService]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectService]

AS
BEGIN
SELECT     SeriveId as SeriveId, ServiceName as ServiceName, IconURL as IconURL
FROM         Services
end

GO
/****** Object:  StoredProcedure [dbo].[SelectServiceProvider]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectServiceProvider]
@LinkUserID AS INT
AS
BEGIN
SELECT     ProviderId, ProvicerName, Location, Email, MobileNo, Longitude, Latitude,Male,Female ,Active, LinkUserID
FROM         ServiceProvider
where LinkUserID=@LinkUserID
END

GO
/****** Object:  StoredProcedure [dbo].[SelectServiceProviderID]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectServiceProviderID]
@SubServiceId as int,
@Male as bit,
@Female as bit
AS
BEGIN

SELECT     ServiceProvider.ProviderId, ServiceProvider.ProvicerName, ServiceProvider.Location, 
ServiceProvider.Email, ServiceProvider.MobileNo, ServiceProvider.Longitude, ServiceProvider.Latitude, 
                      ServiceProvider.Active
FROM         ServiceProvider INNER JOIN
                      Users ON ServiceProvider.LinkUserID = Users.UserId INNER JOIN
                      ProviderServices ON ServiceProvider.ProviderId = ProviderServices.ProviderId
WHERE     (ServiceProvider.Active = 1) and ProviderServices.SubServiceId=@SubServiceId and 
(ServiceProvider.Male=@Male and ServiceProvider.Female=@Female)
END

GO
/****** Object:  StoredProcedure [dbo].[SelectSubServiceByService]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectSubServiceByService]
@ServiceId as int
AS
BEGIN
SELECT     SubServiceId, ServiceId, SubServiceName
FROM         SubServices where ServiceId=@ServiceId
END

GO
/****** Object:  StoredProcedure [dbo].[SelectTransactionsByUserId]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectTransactionsByUserId]
@UserId as int,
@ProviderId as int
AS
BEGIN
SELECT     

	Transactions.TransactionId, 
	ServiceProvider.ProvicerName, 
	Transactions.ChargeAmount, 
	RequestStatus.RequestStatus, 
	PaymentMethods.PaymentName, 
		convert(VARCHAR(10), Transactions.DateAdded, 103) + ' ' + LTRIM(RIGHT(convert(VARCHAR(20), 
		Transactions.DateAdded, 100), 7)) as DateAdded

FROM Transactions INNER JOIN ServiceProvider 

ON Transactions.ProvicerId = ServiceProvider.ProviderId INNER JOIN RequestStatus 
ON Transactions.RequestStatusId = RequestStatus.RequestStatusId INNER JOIN PaymentMethods 
ON Transactions.PaymentMethodsID = PaymentMethods.PaymentID 

where Transactions.UserId=@UserId or ServiceProvider.LinkUserID=@ProviderId and (RequestStatus.RequestStatusId in(1,2))

order by Transactions.DateAdded desc

END

GO
/****** Object:  StoredProcedure [dbo].[SelectUserType]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectUserType] 

AS
BEGIN
SELECT UserTypeId as UserTypeId, UserType as UserType FROM UserType where UserTypeId<>2
END

GO
/****** Object:  StoredProcedure [dbo].[SelectViewOrderByProviderID]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SelectViewOrderByProviderID]
@ProvicerId as int
AS
BEGIN
SELECT     
 Transactions.TransactionId as TransactionId,
 Users.FullName as FullName, 
 Users.MobileNo as MobileNo, 
 SubServices.SubServiceName as SubServiceName, 
 Transactions.ChargeAmount as ChargeAmount, 
 Transactions.Longitude as Longitude, 
 Transactions.Latitude as Latitude,
 SerivcePriority.PrioriyName as PrioriyName, 
 Transactions.RequestStatusId as RequestStatusId, 
 PaymentMethods.PaymentName as PaymentName
FROM Transactions INNER JOIN SubServices 
	
	ON Transactions.SubServiceId = SubServices.SubServiceId INNER JOIN Users 
	ON Transactions.UserId = Users.UserId INNER JOIN SerivcePriority 
	ON Transactions.PriorityId = SerivcePriority.PriorityId INNER JOIN PaymentMethods 
	ON Transactions.PaymentMethodsID = PaymentMethods.PaymentID

where Transactions.RequestStatusId=1 and Transactions.ProvicerId=@ProvicerId
END

GO
/****** Object:  StoredProcedure [dbo].[UpdateTransactionsStatus]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[UpdateTransactionsStatus]
@RequestStatusId as Integer,
@TransactionId as int,
@ProviderId as int
AS
BEGIN
if (@RequestStatusId=2)
begin
update ServiceProvider set Active=0 where ProviderId=@ProviderId
end

UPDATE       Transactions
SET                RequestStatusId =@RequestStatusId where TransactionId=@TransactionId

if (@RequestStatusId=3)
UPDATE       Transactions
SET                RequestStatusId =@RequestStatusId where TransactionId=@TransactionId
if (@RequestStatusId=4)
begin
update ServiceProvider set Active=1 where ProviderId=@ProviderId
end
UPDATE       Transactions
SET                RequestStatusId =@RequestStatusId where TransactionId=@TransactionId
END

GO
/****** Object:  StoredProcedure [dbo].[UpdateUserByActive]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[UpdateUserByActive]
@Active as bit,
@UserId as int
AS
BEGIN
UPDATE    Users
SET              Active =@Active where UserId=@UserId
END

GO
/****** Object:  Table [dbo].[PaymentMethods]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PaymentMethods](
	[PaymentID] [int] IDENTITY(1,1) NOT NULL,
	[PaymentName] [nvarchar](150) NULL,
 CONSTRAINT [PK_PaymentMethods] PRIMARY KEY CLUSTERED 
(
	[PaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ProviderServices]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProviderServices](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ProviderId] [int] NOT NULL,
	[SubServiceId] [int] NOT NULL,
	[InCityCharge] [decimal](18, 2) NOT NULL,
	[OutCityCharge] [decimal](18, 2) NOT NULL,
	[IsActive] [bit] NULL CONSTRAINT [DF_ProviderServices_IsActive]  DEFAULT ((1)),
 CONSTRAINT [PK_ProviderServices] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[RequestStatus]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RequestStatus](
	[RequestStatusId] [int] IDENTITY(1,1) NOT NULL,
	[RequestStatus] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_RequestStatus] PRIMARY KEY CLUSTERED 
(
	[RequestStatusId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[SerivcePriority]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SerivcePriority](
	[PriorityId] [int] IDENTITY(1,1) NOT NULL,
	[PrioriyName] [nvarchar](50) NOT NULL,
	[Priority] [int] NOT NULL,
 CONSTRAINT [PK_SerivcePriority] PRIMARY KEY CLUSTERED 
(
	[PriorityId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ServiceProvider]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ServiceProvider](
	[ProviderId] [int] IDENTITY(1,1) NOT NULL,
	[ProvicerName] [nvarchar](50) NOT NULL,
	[Location] [nvarchar](50) NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[MobileNo] [nvarchar](20) NOT NULL,
	[Longitude] [nvarchar](50) NOT NULL,
	[Latitude] [nvarchar](50) NOT NULL,
	[Active] [bit] NULL,
	[LinkUserID] [int] NULL,
	[Male] [bit] NULL,
	[Female] [bit] NULL,
 CONSTRAINT [PK_ServiceProvider] PRIMARY KEY CLUSTERED 
(
	[ProviderId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Services]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Services](
	[SeriveId] [int] IDENTITY(1,1) NOT NULL,
	[ServiceName] [nvarchar](50) NOT NULL,
	[IconURL] [nvarchar](150) NOT NULL,
	[Description] [nvarchar](150) NULL,
 CONSTRAINT [PK_Services] PRIMARY KEY CLUSTERED 
(
	[SeriveId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[SubServices]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SubServices](
	[SubServiceId] [int] IDENTITY(1,1) NOT NULL,
	[ServiceId] [int] NOT NULL,
	[SubServiceName] [nvarchar](150) NOT NULL,
 CONSTRAINT [PK_SubServices] PRIMARY KEY CLUSTERED 
(
	[SubServiceId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Transactions]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Transactions](
	[TransactionId] [int] IDENTITY(5000,1) NOT NULL,
	[UserId] [int] NOT NULL,
	[SubServiceId] [int] NOT NULL,
	[ProvicerId] [int] NOT NULL,
	[ChargeAmount] [nvarchar](50) NOT NULL,
	[Longitude] [decimal](18, 8) NOT NULL,
	[Latitude] [decimal](18, 8) NOT NULL,
	[PriorityId] [int] NOT NULL,
	[RequestStatusId] [int] NOT NULL,
	[DateAdded] [datetime] NOT NULL,
	[PaymentMethodsID] [int] NULL,
 CONSTRAINT [PK_Transactions] PRIMARY KEY CLUSTERED 
(
	[TransactionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Users]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserId] [int] IDENTITY(1,1) NOT NULL,
	[UserName] [nvarchar](20) NOT NULL,
	[FullName] [nvarchar](20) NOT NULL,
	[Password] [nvarchar](20) NOT NULL,
	[MobileNo] [nvarchar](20) NOT NULL,
	[Email] [nvarchar](40) NULL,
	[UserTypeId] [int] NOT NULL,
	[Balance] [decimal](18, 2) NOT NULL,
	[DateAdded] [datetime] NOT NULL,
	[Active] [bit] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[UserType]    Script Date: 13/04/2018 8:14:23 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserType](
	[UserTypeId] [int] IDENTITY(1,1) NOT NULL,
	[UserType] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_UserType] PRIMARY KEY CLUSTERED 
(
	[UserTypeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET IDENTITY_INSERT [dbo].[PaymentMethods] ON 

INSERT [dbo].[PaymentMethods] ([PaymentID], [PaymentName]) VALUES (1, N'الدفع بالبطاقه الائتمانية')
INSERT [dbo].[PaymentMethods] ([PaymentID], [PaymentName]) VALUES (2, N'الدفع عند الاستلام')
INSERT [dbo].[PaymentMethods] ([PaymentID], [PaymentName]) VALUES (3, N'لدي اشتراك سنوي مسبق')
SET IDENTITY_INSERT [dbo].[PaymentMethods] OFF
SET IDENTITY_INSERT [dbo].[ProviderServices] ON 

INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (1, 1, 2, CAST(120.90 AS Decimal(18, 2)), CAST(130.50 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (2, 1, 3, CAST(30.20 AS Decimal(18, 2)), CAST(40.40 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (3, 1, 4, CAST(50.30 AS Decimal(18, 2)), CAST(100.50 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (4, 1, 5, CAST(20.20 AS Decimal(18, 2)), CAST(10.50 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (7, 1, 12, CAST(32.50 AS Decimal(18, 2)), CAST(31.50 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (8, 1, 9, CAST(55.50 AS Decimal(18, 2)), CAST(55.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (14, 4, 11, CAST(20.00 AS Decimal(18, 2)), CAST(50.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (15, 4, 12, CAST(30.00 AS Decimal(18, 2)), CAST(80.00 AS Decimal(18, 2)), 0)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (16, 4, 12, CAST(90.00 AS Decimal(18, 2)), CAST(140.00 AS Decimal(18, 2)), 0)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (17, 4, 12, CAST(88.00 AS Decimal(18, 2)), CAST(889.00 AS Decimal(18, 2)), 0)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (18, 4, 12, CAST(55.00 AS Decimal(18, 2)), CAST(99.00 AS Decimal(18, 2)), 1)
INSERT [dbo].[ProviderServices] ([Id], [ProviderId], [SubServiceId], [InCityCharge], [OutCityCharge], [IsActive]) VALUES (26, 4, 12, CAST(22.00 AS Decimal(18, 2)), CAST(66.00 AS Decimal(18, 2)), 1)
SET IDENTITY_INSERT [dbo].[ProviderServices] OFF
SET IDENTITY_INSERT [dbo].[RequestStatus] ON 

INSERT [dbo].[RequestStatus] ([RequestStatusId], [RequestStatus]) VALUES (1, N'Pending')
INSERT [dbo].[RequestStatus] ([RequestStatusId], [RequestStatus]) VALUES (2, N'Under Processing')
INSERT [dbo].[RequestStatus] ([RequestStatusId], [RequestStatus]) VALUES (3, N'Canceled')
INSERT [dbo].[RequestStatus] ([RequestStatusId], [RequestStatus]) VALUES (4, N'Finished')
SET IDENTITY_INSERT [dbo].[RequestStatus] OFF
SET IDENTITY_INSERT [dbo].[SerivcePriority] ON 

INSERT [dbo].[SerivcePriority] ([PriorityId], [PrioriyName], [Priority]) VALUES (1, N'حامل', 1)
INSERT [dbo].[SerivcePriority] ([PriorityId], [PrioriyName], [Priority]) VALUES (2, N'اطفال', 2)
INSERT [dbo].[SerivcePriority] ([PriorityId], [PrioriyName], [Priority]) VALUES (3, N'كبار بالسن', 3)
INSERT [dbo].[SerivcePriority] ([PriorityId], [PrioriyName], [Priority]) VALUES (4, N'ذوي احتياجات خاصة', 4)
INSERT [dbo].[SerivcePriority] ([PriorityId], [PrioriyName], [Priority]) VALUES (5, N'غير ذلك', 5)
SET IDENTITY_INSERT [dbo].[SerivcePriority] OFF
SET IDENTITY_INSERT [dbo].[ServiceProvider] ON 

INSERT [dbo].[ServiceProvider] ([ProviderId], [ProvicerName], [Location], [Email], [MobileNo], [Longitude], [Latitude], [Active], [LinkUserID], [Male], [Female]) VALUES (1, N'ورشة زايد', N'الطائف', N't@gmail.com', N'565656565', N'21.2543120', N'40.4496635', 1, 41, 1, 0)
INSERT [dbo].[ServiceProvider] ([ProviderId], [ProvicerName], [Location], [Email], [MobileNo], [Longitude], [Latitude], [Active], [LinkUserID], [Male], [Female]) VALUES (4, N'ورشه الصلاح', N'الطائف', N't@gmail.com', N'8998959598989', N'21.2530822', N'40.4504113', 1, 46, 1, 0)
INSERT [dbo].[ServiceProvider] ([ProviderId], [ProvicerName], [Location], [Email], [MobileNo], [Longitude], [Latitude], [Active], [LinkUserID], [Male], [Female]) VALUES (5, N'عهد', N'الطائف', N'jhh@gmail.com', N'05556987233', N'21.2575583', N'40.4584844', 1, 49, 1, 0)
SET IDENTITY_INSERT [dbo].[ServiceProvider] OFF
SET IDENTITY_INSERT [dbo].[Services] ON 

INSERT [dbo].[Services] ([SeriveId], [ServiceName], [IconURL], [Description]) VALUES (1, N'غسيل السيارات', N'car_wash.png', NULL)
INSERT [dbo].[Services] ([SeriveId], [ServiceName], [IconURL], [Description]) VALUES (2, N'تغيير زيت', N'change_oil.png', NULL)
INSERT [dbo].[Services] ([SeriveId], [ServiceName], [IconURL], [Description]) VALUES (3, N'سطحة', N'truck.png', NULL)
INSERT [dbo].[Services] ([SeriveId], [ServiceName], [IconURL], [Description]) VALUES (4, N'الاطارات', N'flat.png', NULL)
INSERT [dbo].[Services] ([SeriveId], [ServiceName], [IconURL], [Description]) VALUES (5, N'بطارية', N'battery.png', NULL)
INSERT [dbo].[Services] ([SeriveId], [ServiceName], [IconURL], [Description]) VALUES (6, N'تعبئة وقود', N'car_fill.png', NULL)
INSERT [dbo].[Services] ([SeriveId], [ServiceName], [IconURL], [Description]) VALUES (7, N'ورشة متنقلة', N'mobile_workshop.png', NULL)
SET IDENTITY_INSERT [dbo].[Services] OFF
SET IDENTITY_INSERT [dbo].[SubServices] ON 

INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (2, 4, N'تغيير الاطار الاحتياطي')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (3, 4, N'اصلاح الاطار في محطة')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (4, 4, N'اصلاح الاطار في موقع')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (5, 4, N'تعبئة الاطار بالهواء')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (6, 4, N'تغيير الاطار بالمحطة')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (8, 5, N'شحن بطارية')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (9, 5, N'تغيير بطارية')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (10, 3, N'سطحة')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (11, 2, N'تغيير زيت')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (12, 1, N'غسيل السيارات')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (13, 6, N'بنزين 90')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (14, 6, N'بنزين 95')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (15, 6, N'بنزين 98')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (16, 6, N'ديزل')
INSERT [dbo].[SubServices] ([SubServiceId], [ServiceId], [SubServiceName]) VALUES (17, 7, N'ورشة متنقلة')
SET IDENTITY_INSERT [dbo].[SubServices] OFF
SET IDENTITY_INSERT [dbo].[Transactions] ON 

INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5034, 43, 2, 1, N'١٣٠٫٥', CAST(35.91537760 AS Decimal(18, 8)), CAST(31.96230170 AS Decimal(18, 8)), 1, 4, CAST(N'2018-04-05 08:57:42.727' AS DateTime), 2)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5035, 45, 12, 1, N'٣٢٫٥', CAST(21.25431490 AS Decimal(18, 8)), CAST(40.44966530 AS Decimal(18, 8)), 1, 4, CAST(N'2018-04-07 14:54:17.703' AS DateTime), 1)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5036, 45, 12, 4, N'٣٠', CAST(21.25431490 AS Decimal(18, 8)), CAST(40.44966530 AS Decimal(18, 8)), 5, 4, CAST(N'2018-04-07 15:04:08.240' AS DateTime), 3)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5037, 43, 12, 4, N'٣٠', CAST(21.25431490 AS Decimal(18, 8)), CAST(40.44966530 AS Decimal(18, 8)), 1, 4, CAST(N'2018-04-07 15:09:55.570' AS DateTime), 1)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5038, 43, 11, 4, N'٢٠', CAST(21.25431490 AS Decimal(18, 8)), CAST(40.44966530 AS Decimal(18, 8)), 1, 4, CAST(N'2018-04-07 15:10:59.763' AS DateTime), 1)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5039, 45, 12, 1, N'٣٢٫٥', CAST(21.25431490 AS Decimal(18, 8)), CAST(40.44966530 AS Decimal(18, 8)), 5, 4, CAST(N'2018-04-07 15:13:57.790' AS DateTime), 2)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5040, 43, 2, 1, N'١٢٠٫٩', CAST(21.25431200 AS Decimal(18, 8)), CAST(40.44966350 AS Decimal(18, 8)), 1, 4, CAST(N'2018-04-07 15:15:57.180' AS DateTime), 1)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5041, 45, 12, 4, N'٣٠', CAST(40.39751670 AS Decimal(18, 8)), CAST(21.27575990 AS Decimal(18, 8)), 3, 4, CAST(N'2018-04-07 20:40:09.273' AS DateTime), 1)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5042, 45, 2, 1, N'١٢٠٫٩', CAST(21.25431200 AS Decimal(18, 8)), CAST(40.44966350 AS Decimal(18, 8)), 1, 4, CAST(N'2018-04-10 11:44:57.570' AS DateTime), 1)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5046, 43, 12, 4, N'٢٢', CAST(21.25308220 AS Decimal(18, 8)), CAST(40.45041130 AS Decimal(18, 8)), 1, 4, CAST(N'2018-04-13 12:37:29.083' AS DateTime), 1)
INSERT [dbo].[Transactions] ([TransactionId], [UserId], [SubServiceId], [ProvicerId], [ChargeAmount], [Longitude], [Latitude], [PriorityId], [RequestStatusId], [DateAdded], [PaymentMethodsID]) VALUES (5047, 43, 12, 1, N'٣٢٫٥', CAST(39.17663630 AS Decimal(18, 8)), CAST(21.78091790 AS Decimal(18, 8)), 1, 1, CAST(N'2018-04-13 15:20:35.120' AS DateTime), 1)
SET IDENTITY_INSERT [dbo].[Transactions] OFF
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (41, N'22444', N'hhggcf', N'123', N'6568686859', N'y@gmail.com', 3, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-03-16 15:08:43.217' AS DateTime), 1)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (42, N'hhyhu', N'yyhhhhh', N'ghhhhhhhh', N'6655588888', N'y@gmail.com', 1, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-03-16 15:13:11.930' AS DateTime), 0)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (43, N'22123', N'ggggffg', N'123', N'8956966966', N'y@gmail.com', 1, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-03-16 15:15:05.997' AS DateTime), 1)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (44, N'k.agad', N'ahad', N'123', N'0590450188', N'joody.333@windowslive.com', 1, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-03-31 23:06:47.930' AS DateTime), 0)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (45, N'ahadk', N'ahad', N'ab911', N'0590450188', N'joody.333@windowslive.com', 1, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-04-04 16:27:46.320' AS DateTime), 1)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (46, N'1', N'bb', N'123', N'0558888888', N'ahad.333@yahoo.com', 3, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-04-04 16:30:50.130' AS DateTime), 1)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (47, N'tt', N'b6', N'4554', N'0599999999', N'ahad.333@yahoo.com', 1, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-04-04 19:05:51.757' AS DateTime), 0)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (48, N'12', N'gh', N'123', N'0596805744', N'ghjfff@hotmail.com', 3, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-04-12 13:19:58.647' AS DateTime), 0)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (49, N'321', N'321', N'321', N'3059999635', N'joody.333@windowslive.com', 3, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-04-12 13:21:06.127' AS DateTime), 1)
INSERT [dbo].[Users] ([UserId], [UserName], [FullName], [Password], [MobileNo], [Email], [UserTypeId], [Balance], [DateAdded], [Active]) VALUES (50, N'ررر', N'رر', N'xxxx', N'5888444444', N'ahadkamak@hotmail.com', 1, CAST(0.00 AS Decimal(18, 2)), CAST(N'2018-04-13 15:16:38.743' AS DateTime), 0)
SET IDENTITY_INSERT [dbo].[Users] OFF
SET IDENTITY_INSERT [dbo].[UserType] ON 

INSERT [dbo].[UserType] ([UserTypeId], [UserType]) VALUES (1, N'Customer')
INSERT [dbo].[UserType] ([UserTypeId], [UserType]) VALUES (2, N'Administrator')
INSERT [dbo].[UserType] ([UserTypeId], [UserType]) VALUES (3, N'Provider')
SET IDENTITY_INSERT [dbo].[UserType] OFF
ALTER TABLE [dbo].[ProviderServices]  WITH CHECK ADD  CONSTRAINT [FK_ProviderServices_ServiceProvider] FOREIGN KEY([ProviderId])
REFERENCES [dbo].[ServiceProvider] ([ProviderId])
GO
ALTER TABLE [dbo].[ProviderServices] CHECK CONSTRAINT [FK_ProviderServices_ServiceProvider]
GO
ALTER TABLE [dbo].[ProviderServices]  WITH CHECK ADD  CONSTRAINT [FK_ProviderServices_SubServices] FOREIGN KEY([SubServiceId])
REFERENCES [dbo].[SubServices] ([SubServiceId])
GO
ALTER TABLE [dbo].[ProviderServices] CHECK CONSTRAINT [FK_ProviderServices_SubServices]
GO
ALTER TABLE [dbo].[SubServices]  WITH CHECK ADD  CONSTRAINT [FK_SubServices_Services] FOREIGN KEY([ServiceId])
REFERENCES [dbo].[Services] ([SeriveId])
GO
ALTER TABLE [dbo].[SubServices] CHECK CONSTRAINT [FK_SubServices_Services]
GO
ALTER TABLE [dbo].[Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Transactions_PaymentMethods] FOREIGN KEY([PaymentMethodsID])
REFERENCES [dbo].[PaymentMethods] ([PaymentID])
GO
ALTER TABLE [dbo].[Transactions] CHECK CONSTRAINT [FK_Transactions_PaymentMethods]
GO
ALTER TABLE [dbo].[Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Transactions_RequestStatus] FOREIGN KEY([RequestStatusId])
REFERENCES [dbo].[RequestStatus] ([RequestStatusId])
GO
ALTER TABLE [dbo].[Transactions] CHECK CONSTRAINT [FK_Transactions_RequestStatus]
GO
ALTER TABLE [dbo].[Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Transactions_SerivcePriority] FOREIGN KEY([PriorityId])
REFERENCES [dbo].[SerivcePriority] ([PriorityId])
GO
ALTER TABLE [dbo].[Transactions] CHECK CONSTRAINT [FK_Transactions_SerivcePriority]
GO
ALTER TABLE [dbo].[Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Transactions_ServiceProvider] FOREIGN KEY([ProvicerId])
REFERENCES [dbo].[ServiceProvider] ([ProviderId])
GO
ALTER TABLE [dbo].[Transactions] CHECK CONSTRAINT [FK_Transactions_ServiceProvider]
GO
ALTER TABLE [dbo].[Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Transactions_SubServices] FOREIGN KEY([SubServiceId])
REFERENCES [dbo].[SubServices] ([SubServiceId])
GO
ALTER TABLE [dbo].[Transactions] CHECK CONSTRAINT [FK_Transactions_SubServices]
GO
ALTER TABLE [dbo].[Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Transactions_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([UserId])
GO
ALTER TABLE [dbo].[Transactions] CHECK CONSTRAINT [FK_Transactions_Users]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_UserType] FOREIGN KEY([UserTypeId])
REFERENCES [dbo].[UserType] ([UserTypeId])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_UserType]
GO
USE [master]
GO
ALTER DATABASE [DarbDB] SET  READ_WRITE 
GO
