Imports System.Web
Imports System.Web.Services
Imports System.Web.Services.Protocols
Imports System.Data.SqlClient
Imports System.Data
Imports System.Diagnostics
Imports CryptoMethod.CM
Imports System.IO

' To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line.
' <System.Web.Script.Services.ScriptService()> _
<WebService(Namespace:="http://tempuri.org/")> _
<WebServiceBinding(ConformsTo:=WsiProfiles.BasicProfile1_1)> _
<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Public Class Service
    Inherits System.Web.Services.WebService

#Region "Decleration"
    Dim connectionString As clsConnectionString
    Dim command As SqlCommand
    Dim adapter As SqlDataAdapter
    Dim Passencrpt As String

    Dim ds As DataSet
#End Region

#Region "Function"

#Region "InsertUsers"
    <WebMethod()> _
    Public Function InsertUsers(ByVal UserId As Integer, ByVal UserName As String, ByVal FullName As String _
                                , ByVal Password As String, ByVal MobileNo As String, ByVal Email As String _
                                , ByVal UserTypeId As Integer, ByVal Balance As String, ByVal Active As Boolean) As Integer
        Try
            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("InsertUsers", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure

            command.Parameters.Add("@UserName", SqlDbType.NVarChar).Value = UserName
            command.Parameters.Add("@Password", SqlDbType.NVarChar).Value = Password
            command.Parameters.Add("@FullName", SqlDbType.NVarChar).Value = FullName
            command.Parameters.Add("@MobileNo", SqlDbType.NVarChar).Value = MobileNo
            command.Parameters.Add("@Email", SqlDbType.NVarChar).Value = Email
            command.Parameters.Add("@Balance", SqlDbType.NVarChar).Value = Balance
            command.Parameters.Add("@UserTypeId", SqlDbType.Int).Value = UserTypeId
            command.Parameters.Add("@Active", SqlDbType.Bit).Value = Active
            command.Parameters.Add("@UserId", SqlDbType.Int).Value = UserId
            command.Parameters.Add("@ID", SqlDbType.Int).Direction = ParameterDirection.Output

            command.Connection = connectionString.conn
            If (connectionString.conn.State = ConnectionState.Closed) Then
                connectionString.conn.Open()
            End If
            Return CInt(command.ExecuteScalar())
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
            Return False
        End Try
    End Function
#End Region

#Region "SelectLogin"
    <WebMethod()> _
    Public Function SelectLogin(ByVal UserName As String, ByVal Password As String) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectLogin", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            command.Parameters.Add("@UserName", SqlDbType.Nvarchar).Value = UserName
            command.Parameters.Add("@Password", SqlDbType.Nvarchar).Value = Password

            ds = New DataSet()
            adapter.Fill(ds)
            command.Connection = connectionString.conn

            If (connectionString.conn.State = ConnectionState.Closed) Then
                connectionString.conn.Open()
            End If

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectSubServiceByService"
    <WebMethod()> _
    Public Function SelectSubServiceByService(ByVal ServiceId As Integer) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectSubServiceByService", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            command.Parameters.Add("@ServiceId", SqlDbType.Nvarchar).Value = ServiceId

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectService"
    <WebMethod()> _
    Public Function SelectService() As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectService", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectPriority"
    <WebMethod()> _
    Public Function SelectPriority() As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectPriority", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectServiceProviderID"
    <WebMethod()> _
    Public Function SelectServiceProviderID(ByVal SubServiceId As Integer, ByVal Male As Boolean, ByVal Female As Boolean) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectServiceProviderID", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)
            command.Parameters.Add("@SubServiceId", SqlDbType.Int).Value = SubServiceId
            command.Parameters.Add("@Male", SqlDbType.Bit).Value = Male
            command.Parameters.Add("@Female", SqlDbType.Bit).Value = Female
            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectViewOrderByProviderID"
    <WebMethod()> _
    Public Function SelectViewOrderByProviderID(ByVal ProvicerId As Integer) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectViewOrderByProviderID", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)
            command.Parameters.Add("@ProvicerId", SqlDbType.Int).Value = ProvicerId
            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectUserType"
    <WebMethod()> _
    Public Function SelectUserType() As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectUserType", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "UpdateUserByActive"
    <WebMethod()> _
    Public Function UpdateUserByActive(ByVal UserId As Integer, ByVal Active As Boolean) As Integer
        Try
            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("UpdateUserByActive", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure

            command.Parameters.Add("@UserId", SqlDbType.Int).Value = UserId
            command.Parameters.Add("@Active", SqlDbType.Bit).Value = Active

            command.Connection = connectionString.conn
            If (connectionString.conn.State = ConnectionState.Closed) Then
                connectionString.conn.Open()
            End If
            command.ExecuteNonQuery()
            Return 1
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
            Return 0
        End Try
    End Function
#End Region

#Region "SelectPaymentMethods"
    <WebMethod()> _
    Public Function SelectPaymentMethods() As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectPaymentMethods", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectProviderServices"
    <WebMethod()> _
    Public Function SelectProviderServices(ByVal ProviderId As Integer, ByVal SubServiceId As Integer) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectProviderServices", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            command.Parameters.Add("@ProviderId", SqlDbType.Int).Value = ProviderId
            command.Parameters.Add("@SubServiceId", SqlDbType.Int).Value = SubServiceId

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "InsertTransactions"
    <WebMethod()> _
    Public Function InsertTransactions(ByVal UserId As Integer, ByVal SubServiceId As Integer, ByVal ProvicerId As Integer, ByVal ChargeAmount As String _
                                       , ByVal Longitude As String, ByVal Latitude As String _
                                , ByVal PriorityId As Integer, ByVal RequestStatusId As Integer, ByVal PaymentMethodsID As Integer) As Integer
        Try
            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("InsertTransactions", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure

            command.Parameters.Add("@PaymentMethodsID", SqlDbType.Int).Value = PaymentMethodsID
            command.Parameters.Add("@SubServiceId", SqlDbType.Int).Value = SubServiceId
            command.Parameters.Add("@RequestStatusId", SqlDbType.Int).Value = RequestStatusId
            command.Parameters.Add("@PriorityId", SqlDbType.Int).Value = PriorityId
            command.Parameters.Add("@Latitude", SqlDbType.NVarChar).Value = Latitude
            command.Parameters.Add("@Longitude", SqlDbType.NVarChar).Value = Longitude
            command.Parameters.Add("@ChargeAmount", SqlDbType.NVarChar).Value = ChargeAmount
            command.Parameters.Add("@ProvicerId", SqlDbType.Int).Value = ProvicerId
            command.Parameters.Add("@UserId", SqlDbType.Int).Value = UserId
            command.Parameters.Add("@ID", SqlDbType.Int).Direction = ParameterDirection.Output

            command.Connection = connectionString.conn

            If (connectionString.conn.State = ConnectionState.Closed) Then
                connectionString.conn.Open()
            End If

            Return CInt(command.ExecuteScalar())
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
            Return False
        End Try
    End Function
#End Region

#Region "SelectTransactionsByUserId"
    <WebMethod()> _
    Public Function SelectTransactionsByUserId(ByVal UserId As Integer, ByVal ProviderId As Integer) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectTransactionsByUserId", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            command.Parameters.Add("@UserId", SqlDbType.Int).Value = UserId
            command.Parameters.Add("@ProviderId", SqlDbType.Int).Value = ProviderId

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "SelectServiceProvider"
    <WebMethod()> _
    Public Function SelectServiceProvider(ByVal LinkUserID As Integer) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectServiceProvider", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            command.Parameters.Add("@LinkUserID", SqlDbType.Int).Value = LinkUserID

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "InsertServiceProvider"
    <WebMethod()> _
    Public Function InsertServiceProvider(ByVal ProviderId As Integer, ByVal ProvicerName As String, ByVal Location As String, ByVal Email As String, ByVal MobileNo As String _
                                          , ByVal Longitude As String, ByVal Latitude As String, ByVal Male As Boolean, ByVal Female As Boolean, ByVal LinkUserID As Integer) As Integer
        Try
            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("InsertServiceProvider", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure

            command.Parameters.Add("@LinkUserID", SqlDbType.Int).Value = LinkUserID
            command.Parameters.Add("@Latitude", SqlDbType.NVarChar).Value = Latitude
            command.Parameters.Add("@Longitude", SqlDbType.NVarChar).Value = Longitude
            command.Parameters.Add("@MobileNo", SqlDbType.NVarChar).Value = MobileNo
            command.Parameters.Add("@Email", SqlDbType.NVarChar).Value = Email
            command.Parameters.Add("@Location", SqlDbType.NVarChar).Value = Location
            command.Parameters.Add("@Male", SqlDbType.Bit).Value = Male
            command.Parameters.Add("@Female", SqlDbType.Bit).Value = Female
            command.Parameters.Add("@ProvicerName", SqlDbType.NVarChar).Value = ProvicerName
            command.Parameters.Add("@ProviderId", SqlDbType.Int).Value = ProviderId
            command.Connection = connectionString.conn
            If (connectionString.conn.State = ConnectionState.Closed) Then
                connectionString.conn.Open()
            End If

            Return command.ExecuteNonQuery()
            Return True
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
            Return False
        End Try
    End Function
#End Region

#Region "SelectProviderServicesByProvicerId"
    <WebMethod()> _
    Public Function SelectProviderServicesByProvicerId(ByVal ProviderId As Integer) As DataTable
        Try

            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("SelectProviderServicesByProvicerId", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure
            adapter = New SqlDataAdapter(command)

            command.Parameters.Add("@ProviderId", SqlDbType.Int).Value = ProviderId

            ds = New DataSet()
            adapter.Fill(ds)

            Return ds.Tables(0)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try
    End Function
#End Region

#Region "InsertProviderServices"
    <WebMethod()> _
    Public Function InsertProviderServices(ByVal Id As Integer, ByVal ProviderId As Integer _
                                           , ByVal SubServiceId As Integer _
                                           , ByVal InCityCharge As String, ByVal OutCityCharge As String, ByVal Flag As Integer) As Integer
        Try
            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("InsertProviderServices", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure

            command.Parameters.Add("@OutCityCharge", SqlDbType.NVarChar).Value = OutCityCharge
            command.Parameters.Add("@InCityCharge", SqlDbType.NVarChar).Value = InCityCharge
            command.Parameters.Add("@SubServiceId", SqlDbType.Int).Value = SubServiceId
            command.Parameters.Add("@ProviderId", SqlDbType.Int).Value = ProviderId
            command.Parameters.Add("@Id", SqlDbType.Int).Value = Id
            command.Parameters.Add("@Flag", SqlDbType.Int).Value = Flag

            command.Connection = connectionString.conn
            If (connectionString.conn.State = ConnectionState.Closed) Then
                connectionString.conn.Open()
            End If

            Return command.ExecuteNonQuery()
            Return True
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
            Return False
        End Try
    End Function
#End Region

#Region "UpdateTransactionsStatus"
    <WebMethod()> _
    Public Function UpdateTransactionsStatus(ByVal RequestStatusId As Integer, ByVal TransactionId As Integer, ByVal ProviderId As Integer) As Integer
        Try
            command = New SqlCommand()
            connectionString = New clsConnectionString()
            command = New SqlCommand("UpdateTransactionsStatus", connectionString.conn)
            command.CommandType = CommandType.StoredProcedure

            command.Parameters.Add("@RequestStatusId", SqlDbType.Int).Value = RequestStatusId
            command.Parameters.Add("@TransactionId", SqlDbType.Int).Value = TransactionId
            command.Parameters.Add("@ProviderId", SqlDbType.Int).Value = ProviderId

            command.Connection = connectionString.conn
            If (connectionString.conn.State = ConnectionState.Closed) Then
                connectionString.conn.Open()
            End If
            command.ExecuteNonQuery()
            Return 1
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
            Return 0
        End Try
    End Function
#End Region
#End Region
End Class