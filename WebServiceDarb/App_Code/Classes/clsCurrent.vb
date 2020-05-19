Module clsCurrent

#Region "Proprity"

#Region "UserID"

    Private _UuserID As Integer
    Public Property UserID() As Integer
        Get
            Return _UuserID
        End Get
        Set(ByVal value As Integer)
            _UuserID = value
        End Set
    End Property
#End Region

#Region "FillName"
    Private _FillName As String
    Public Property FillName() As String
        Get
            Return _FillName
        End Get
        Set(ByVal value As String)
            _FillName = value
        End Set
    End Property
#End Region

#Region "CountID"
    Private _CountID As String
    Public Property CountID() As String
        Get
            Return _CountID
        End Get
        Set(ByVal value As String)
            _CountID = value
        End Set
    End Property
#End Region

#End Region

End Module
