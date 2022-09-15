USE [master]
GO
/****** Object:  Database [assignment_java6]    Script Date: 9/15/2022 10:23:13 PM ******/
CREATE DATABASE [assignment_java6]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'assignment_java6', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\assignment_java6.mdf' , SIZE = 3264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'assignment_java6_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\assignment_java6_log.ldf' , SIZE = 832KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [assignment_java6] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [assignment_java6].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [assignment_java6] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [assignment_java6] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [assignment_java6] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [assignment_java6] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [assignment_java6] SET ARITHABORT OFF 
GO
ALTER DATABASE [assignment_java6] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [assignment_java6] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [assignment_java6] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [assignment_java6] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [assignment_java6] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [assignment_java6] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [assignment_java6] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [assignment_java6] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [assignment_java6] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [assignment_java6] SET  ENABLE_BROKER 
GO
ALTER DATABASE [assignment_java6] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [assignment_java6] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [assignment_java6] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [assignment_java6] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [assignment_java6] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [assignment_java6] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [assignment_java6] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [assignment_java6] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [assignment_java6] SET  MULTI_USER 
GO
ALTER DATABASE [assignment_java6] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [assignment_java6] SET DB_CHAINING OFF 
GO
ALTER DATABASE [assignment_java6] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [assignment_java6] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [assignment_java6] SET DELAYED_DURABILITY = DISABLED 
GO
USE [assignment_java6]
GO
/****** Object:  Table [dbo].[brand_types]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[brand_types](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[description] [ntext] NULL,
	[slug] [varchar](255) NOT NULL,
	[isDeleted] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[order_details]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_details](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[orderId] [bigint] NULL,
	[productId] [bigint] NULL,
	[price] [decimal](12, 3) NOT NULL,
	[quantity] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[orders]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[orders](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[userId] [bigint] NULL,
	[address] [nvarchar](255) NOT NULL,
	[phone] [varchar](11) NOT NULL,
	[createdDate] [datetime] NOT NULL DEFAULT (getdate()),
	[totalPrice] [decimal](12, 3) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[product_types]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[product_types](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[description] [ntext] NULL,
	[slug] [varchar](255) NOT NULL,
	[isDeleted] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[products]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[products](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[typeId] [tinyint] NULL,
	[brandId] [tinyint] NULL,
	[quantity] [int] NOT NULL,
	[price] [decimal](12, 3) NOT NULL,
	[unitId] [tinyint] NULL,
	[imgUrl] [varchar](255) NULL,
	[description] [ntext] NULL,
	[slug] [varchar](255) NOT NULL,
	[isDeleted] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[roles]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[description] [nvarchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[unit_types]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[unit_types](
	[id] [tinyint] IDENTITY(1,1) NOT NULL,
	[description] [ntext] NULL,
	[isDeleted] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[users]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[users](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[username] [varchar](20) NOT NULL,
	[fullname] [nvarchar](50) NULL,
	[hashPassword] [varchar](255) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[createdDate] [datetime] NOT NULL DEFAULT (getdate()),
	[imgUrl] [varchar](255) NULL,
	[roleId] [tinyint] NULL,
	[resetPassword] [varchar](30) NULL,
	[active] [varchar](30) NULL,
	[isDeleted] [bit] NOT NULL DEFAULT ((0)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[brand_types] ON 

INSERT [dbo].[brand_types] ([id], [description], [slug], [isDeleted]) VALUES (1, N'Tissot', N'tissot', 0)
INSERT [dbo].[brand_types] ([id], [description], [slug], [isDeleted]) VALUES (2, N'Omega', N'omega', 0)
INSERT [dbo].[brand_types] ([id], [description], [slug], [isDeleted]) VALUES (3, N'Hamilton', N'hamilton', 0)
INSERT [dbo].[brand_types] ([id], [description], [slug], [isDeleted]) VALUES (4, N'Seiko', N'seiko', 0)
INSERT [dbo].[brand_types] ([id], [description], [slug], [isDeleted]) VALUES (5, N'Longines', N'longines', 0)
SET IDENTITY_INSERT [dbo].[brand_types] OFF
SET IDENTITY_INSERT [dbo].[order_details] ON 

INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (1, 1, 5, CAST(449.990 AS Decimal(12, 3)), 3)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (2, 2, 1, CAST(254.990 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (3, 2, 2, CAST(299.990 AS Decimal(12, 3)), 2)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (4, 2, 3, CAST(259.990 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (5, 3, 1, CAST(254.990 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (6, 3, 2, CAST(299.990 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (7, 3, 3, CAST(259.990 AS Decimal(12, 3)), 3)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (8, 4, 1, CAST(254.990 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (9, 4, 2, CAST(299.990 AS Decimal(12, 3)), 2)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (10, 4, 3, CAST(259.990 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (11, 5, 10, CAST(4995.000 AS Decimal(12, 3)), 1)
INSERT [dbo].[order_details] ([id], [orderId], [productId], [price], [quantity]) VALUES (12, 5, 11, CAST(1317.000 AS Decimal(12, 3)), 2)
SET IDENTITY_INSERT [dbo].[order_details] OFF
SET IDENTITY_INSERT [dbo].[orders] ON 

INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate], [totalPrice]) VALUES (1, 1, N'HCM', N'0345250260', CAST(N'2022-04-11 17:49:40.857' AS DateTime), CAST(1499.970 AS Decimal(12, 3)))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate], [totalPrice]) VALUES (2, 1, N'Tân Phú - Đồng Nai', N'0345250260', CAST(N'2022-06-18 00:10:40.940' AS DateTime), CAST(1114.960 AS Decimal(12, 3)))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate], [totalPrice]) VALUES (3, 1, N'123', N'123', CAST(N'2022-07-22 19:56:36.117' AS DateTime), CAST(1334.950 AS Decimal(12, 3)))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate], [totalPrice]) VALUES (4, 2, N'Hà Nội', N'0345123123', CAST(N'2022-09-15 22:04:03.177' AS DateTime), CAST(1114.960 AS Decimal(12, 3)))
INSERT [dbo].[orders] ([id], [userId], [address], [phone], [createdDate], [totalPrice]) VALUES (5, 3, N'Đà Nẵng', N'0999333999', CAST(N'2022-09-15 22:05:08.223' AS DateTime), CAST(7629.000 AS Decimal(12, 3)))
SET IDENTITY_INSERT [dbo].[orders] OFF
SET IDENTITY_INSERT [dbo].[product_types] ON 

INSERT [dbo].[product_types] ([id], [description], [slug], [isDeleted]) VALUES (1, N'Đồng hồ', N'watches', 0)
INSERT [dbo].[product_types] ([id], [description], [slug], [isDeleted]) VALUES (2, N'Đồng hồ cơ', N'wristwatch', 0)
SET IDENTITY_INSERT [dbo].[product_types] OFF
SET IDENTITY_INSERT [dbo].[products] ON 

INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (1, N'T-Race Motogp 2018 Chronograph', 1, 1, 3, CAST(254.990 AS Decimal(12, 3)), 2, N'popular1.png', N'Đồng hồ nam', N't-race-motogp-2018-chronograph', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (2, N'Le Locle Automatic Silver Dial', 1, 1, 5, CAST(299.990 AS Decimal(12, 3)), 1, N'popular2.png', N'Đồng hồ nam', N'le-locle-automatic-silver-dial', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (3, N'Couturier Automatic Black Dial', 1, 1, 2, CAST(259.990 AS Decimal(12, 3)), 1, N'popular3.png', N'Đồng hồ nam', N'couturier-automatic-black-dial', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (4, N'Speedmaster Racing Automatic Chronograph', 2, 2, 3, CAST(3350.000 AS Decimal(12, 3)), 1, N'popular4.png', N'Đồng hồ nữ', N'speedmaster-racing-automatic-chronograph', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (5, N'Khaki Field Automatic Black Dial', 1, 3, 10, CAST(449.990 AS Decimal(12, 3)), 1, N'popular5.png', N'Đồng hồ nam', N'khaki-field-automatic-black-dial', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (6, N'Seamaster Automatic Chronometer Black', 2, 2, 3, CAST(4500.000 AS Decimal(12, 3)), 1, N'popular6.png', N'Đồng hồ nam', N'seamaster-automatic-chronometer-black', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (7, N'V8 Automatic Chronograph', 1, 1, 15, CAST(4500.000 AS Decimal(12, 3)), 1, N'popular1.png', N'Đồng hồ nữ', N'v8-automatic-chronograph', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (8, N'Automatic Silver Open Heart Dial', 1, 4, 25, CAST(199.990 AS Decimal(12, 3)), 1, N'popular2.png', N'Đồng hồ nam', N'automatic-silver-open-heart-dial', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (9, N'Khaki Navy Scuba Automatic Batman Bezel', 1, 3, 5, CAST(515.000 AS Decimal(12, 3)), 1, N'popular3.png', N'Đồng hồ nam', N'khaki-navy-scuba-automatic-batman-bezel', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (10, N'Seamaster Automatic Blue Dial Steel', 2, 2, 0, CAST(4995.000 AS Decimal(12, 3)), 1, N'popular4.png', N'Đồng hồ nam', N'seamaster-automatic-blue-dial-steel', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (11, N'Hydroconquest Automatic Grey Dial', 2, 5, 5, CAST(1317.000 AS Decimal(12, 3)), 1, N'popular5.png', N'Đồng hồ nam', N'hydroconquest-automatic-grey-dial', 0)
INSERT [dbo].[products] ([id], [name], [typeId], [brandId], [quantity], [price], [unitId], [imgUrl], [description], [slug], [isDeleted]) VALUES (12, N'5 Automatic Black Dial Stainless Steel', 1, 4, 12, CAST(89.990 AS Decimal(12, 3)), 1, N'popular6.png', N'Đồng hồ nam', N'5-automatic-black-dial-stainless-steel', 0)
SET IDENTITY_INSERT [dbo].[products] OFF
SET IDENTITY_INSERT [dbo].[roles] ON 

INSERT [dbo].[roles] ([id], [description]) VALUES (1, N'admin')
INSERT [dbo].[roles] ([id], [description]) VALUES (2, N'user')
SET IDENTITY_INSERT [dbo].[roles] OFF
SET IDENTITY_INSERT [dbo].[unit_types] ON 

INSERT [dbo].[unit_types] ([id], [description], [isDeleted]) VALUES (1, N'Chiếc', 0)
INSERT [dbo].[unit_types] ([id], [description], [isDeleted]) VALUES (2, N'Bộ', 0)
SET IDENTITY_INSERT [dbo].[unit_types] OFF
SET IDENTITY_INSERT [dbo].[users] ON 

INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [roleId], [resetPassword], [active], [isDeleted]) VALUES (1, N'duyhm', N'Hoàng Minh Duy', N'$2a$10$7YIj5SGLAAev58xzTj6y3Owj/0e9ZVBZy9Hoc0nXxeysm8wovQUY6', N'duyhmps16073@fpt.edu.vn', CAST(N'2022-06-12 15:51:28.160' AS DateTime), N'304795906_3249644208641561_539363861062567292_n2.png', 1, NULL, NULL, 0)
INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [roleId], [resetPassword], [active], [isDeleted]) VALUES (2, N'danglb', N'Lê Bảo Đăng', N'$2a$10$kwSVD4IaWrSSaaXxgqmW9OGq9JeSqrmsoIFHsMbgBhcbVCWYgAZTq', N'minhduy125811@gmail.com', CAST(N'2022-06-12 15:51:28.160' AS DateTime), N'danglb.jpg', 2, NULL, NULL, 0)
INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [roleId], [resetPassword], [active], [isDeleted]) VALUES (3, N'lamnh', N'Nguyễn Hùng Lâm', N'$2a$10$kwSVD4IaWrSSaaXxgqmW9OGq9JeSqrmsoIFHsMbgBhcbVCWYgAZTq', N'hunglam@gmail.com', CAST(N'2022-06-12 15:51:28.160' AS DateTime), N'lamnh.jpg', 2, NULL, NULL, 0)
INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [roleId], [resetPassword], [active], [isDeleted]) VALUES (4, N'test1', N'Testt', N'$2a$10$fs32fGj8RJ6am5rc5UBH8.vghc2jr3aZKX3zGUbDXu9QOx9CAriVO', N'minhduy12581@gmail.com', CAST(N'2022-06-12 22:25:20.960' AS DateTime), N'favicon-mu.png', 2, NULL, NULL, 0)
INSERT [dbo].[users] ([id], [username], [fullname], [hashPassword], [email], [createdDate], [imgUrl], [roleId], [resetPassword], [active], [isDeleted]) VALUES (6, N'test2', N'testtt', N'$2a$10$W3lDbetotwWiaVw5o0xf9.8THAO1xpBFD0zWvj4nLQffo4pYvzh.q', N'test2@abc.com', CAST(N'2022-09-15 17:51:09.487' AS DateTime), NULL, 2, NULL, N'4lCh2FXmE4y55qrP1jSN49B80PGtps', 0)
SET IDENTITY_INSERT [dbo].[users] OFF
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__products__32DD1E4CA185A51F]    Script Date: 9/15/2022 10:23:13 PM ******/
ALTER TABLE [dbo].[products] ADD UNIQUE NONCLUSTERED 
(
	[slug] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__roles__489B0D9772C75D3E]    Script Date: 9/15/2022 10:23:13 PM ******/
ALTER TABLE [dbo].[roles] ADD UNIQUE NONCLUSTERED 
(
	[description] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__users__AB6E61643DB7CA87]    Script Date: 9/15/2022 10:23:13 PM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UQ__users__F3DBC572AC1FEB0F]    Script Date: 9/15/2022 10:23:13 PM ******/
ALTER TABLE [dbo].[users] ADD UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([orderId])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([productId])
REFERENCES [dbo].[products] ([id])
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD FOREIGN KEY([userId])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([brandId])
REFERENCES [dbo].[brand_types] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([typeId])
REFERENCES [dbo].[product_types] ([id])
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD FOREIGN KEY([unitId])
REFERENCES [dbo].[unit_types] ([id])
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD FOREIGN KEY([roleId])
REFERENCES [dbo].[roles] ([id])
GO
/****** Object:  StoredProcedure [dbo].[sp_getTotalPricePerMonth]    Script Date: 9/15/2022 10:23:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROC [dbo].[sp_getTotalPricePerMonth]
(
	@month varchar(2),
	@year varchar(4)
)
AS BEGIN
	DECLARE @result varchar(12)
	SET @result = (SELECT
						SUM(order_details.price * order_details.quantity) as 'totalPrice'
					FROM
						orders
						INNER JOIN order_details ON orders.id = order_details.orderId
					WHERE
						MONTH(orders.createdDate) = @month
						AND YEAR(orders.createdDate) = @year)
	IF @result IS NULL BEGIN SET @result = '0' END
	SELECT @result
END

GO
USE [master]
GO
ALTER DATABASE [assignment_java6] SET  READ_WRITE 
GO
