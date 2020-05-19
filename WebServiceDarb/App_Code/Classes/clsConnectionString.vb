Imports System.Data
Imports System.Data.SqlClient
Imports System.Configuration
Imports System
Imports Microsoft.VisualBasic
Imports CryptoMethod.CM
Public Class clsConnectionString
    Inherits System.Web.Services.WebService

#Region "Decleration"
    Public conn As SqlConnection
#End Region

    Sub New()
        Try
            Dim ConnectionSring As String = ConfigurationManager.ConnectionStrings("connAndroid").ToString()
            conn = New SqlConnection(ConnectionSring)
        Catch ex As Exception
            clsLog.ErrorLog(Server.MapPath("Logs/ErrorLog"), ex.Source + vbNewLine + ex.StackTrace + vbNewLine + ex.Message + vbNewLine)
        End Try

    End Sub
End Class
