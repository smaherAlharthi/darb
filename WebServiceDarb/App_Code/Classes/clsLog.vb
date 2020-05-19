Imports System.IO
Imports System
Imports System.Web


Public Module clsLog

#Region "Declarations"
    Dim sLogFormat As String
    Dim sErrorTime As String
#End Region

#Region "Constructor"
    Sub New()

    End Sub
#End Region


    Public Sub ErrorLog(ByVal sPathName As String, ByVal sErrMsg As String)
        sLogFormat = DateTime.Now.ToShortDateString().ToString() + " " + DateTime.Now.ToLongTimeString().ToString() + " =========================================================" + vbNewLine

        'this variable used to create log filename format "
        'for example filename : ErrorLogYYYYMMDD
        Dim sYear As String = DateTime.Now.Year.ToString()
        Dim sMonth As String = DateTime.Now.Month.ToString()
        Dim sDay As String = DateTime.Now.Day.ToString()
        sErrorTime = sYear + sMonth + sDay
        Dim sw As StreamWriter = New StreamWriter("C:\inetpub\wwwroot\WebServiceDarb\Log\" + sErrorTime + ".txt", True)
        sw.WriteLine(sLogFormat + sErrMsg)
        sw.Flush()
        sw.Close()
    End Sub

End Module

